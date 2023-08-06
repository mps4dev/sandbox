package pl.mps.tokenio.sandbox;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.mps.tokenio.sandbox.client.TokenIoClient;
import pl.mps.tokenio.sandbox.config.ApplicationConfig;
import pl.mps.tokenio.sandbox.exception.ConfigurationException;
import pl.mps.tokenio.sandbox.member.Member;
import pl.mps.tokenio.sandbox.model.account.Account;
import pl.mps.tokenio.sandbox.model.account.AccountDetails;
import pl.mps.tokenio.sandbox.model.account.identifier.AccountIdentifier;
import pl.mps.tokenio.sandbox.model.balance.Balance;
import pl.mps.tokenio.sandbox.model.bank.BankServiceStatus;
import pl.mps.tokenio.sandbox.model.bank.BankServicesStatus;
import pl.mps.tokenio.sandbox.model.transaction.Transaction;
import pl.mps.tokenio.sandbox.product.Product;
import spark.Spark;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static pl.mps.tokenio.sandbox.utils.TokenIoUtils.generateNonce;

public class SandboxApplication {

	private final ApplicationConfig config;

	public SandboxApplication() {
		this.config = getAppConfig();
	}

	public static void main(String[] args) {
		new SandboxApplication().start();
	}

	public void start() {
		Member member = getMember();
		TokenIoClient client = new TokenIoClient(member.getApiKey());
		Spark.port(config.getPort());
		setupCallback(client);
		List<Product> products = checkBankConnection(client, member);
		setupAis(client, member, products);
		setupPis(products);
		setupMainPage(member);
	}

	private List<Product> checkBankConnection(TokenIoClient client, Member member) {
		String bankId = config.getBankId();
		List<Product> products = new ArrayList<>();
		if (!bankId.isEmpty()) {
			client.checkIfBankAvailableForMember(bankId, member.getId());
			BankServicesStatus bankServicesStatus = client.getBankServicesStatus(bankId);
			if (bankServicesStatus.getAisStatus() == BankServiceStatus.LIVE) {
				products.add(Product.AIS);
			}
			if (bankServicesStatus.getPisStatus() == BankServiceStatus.LIVE) {
				products.add(Product.PIS);
			}
		}
		return products;
	}

	private void setupCallback(TokenIoClient client) {
		Spark.get(
				"/callback",
				(req, res) -> {
					res.status(200);
						return fetchData(client, req.queryParams("tokenId"));
				});
	}

	private void setupAis(TokenIoClient client, Member member, List<Product> products) {
		Spark.get("/ais", (req, res) -> {
			if (!products.contains(Product.AIS)) {
				return String.format("Bank %s not available for %s", config.getBankId(), Product.AIS);
			}
			String csrfToken = generateNonce();
			res.cookie("csrf_token", csrfToken);
			String redirectUrl = config.getCallbackUrl();
			String tokenRequestUrl = client.getTokenRequestUrl(member, config.getBankId(), redirectUrl, config.isWebApp());
			res.status(302);
			res.redirect(tokenRequestUrl);
			return null;
		});
	}

	private void setupPis(List<Product> products) {
		Spark.get("/pis", (req, res) -> {
			if (!products.contains(Product.PIS)) {
				return String.format("Bank %s not available for %s", config.getBankId(), Product.PIS);
			}
			return  "Not implemented yet";
		});
	}

	private static void setupMainPage(Member member) {
		try {
			String script = Resources.toString(Resources.getResource("script.js"), Charsets.UTF_8).replace("{alias}", member.getValue());
			Spark.get("/script.js", (req, res) -> script);
			String style = Resources.toString(Resources.getResource("style.css"), Charsets.UTF_8);
			Spark.get("/style.css", (req, res) -> {
				res.type("text/css");
				return style;
			});
			String page = Resources.toString(Resources.getResource("index.html"), Charsets.UTF_8);
			Spark.get("/", (req, res) -> page);
		} catch (IOException e) {
			throw new ConfigurationException(e.getMessage());
		}
	}

	private String fetchData(TokenIoClient client, String accessTokenId) {
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.append("<h1>Aggregation results</h1>");
		fetchAccounts(client, accessTokenId, outputBuilder);
		return outputBuilder.toString();
	}

	private void fetchAccounts(TokenIoClient client, String accessToken, StringBuilder outputBuilder) {
		List<Account> accounts = client.getAccounts(accessToken);

		for (Account account : accounts) {
			AccountDetails accountDetails = account.getAccountDetails();
			Balance balance = client.getAccountBalances(account.getId(), accessToken).get(0).getBalance();

			outputBuilder.append("<h2>Account</h2>");

			outputBuilder.append("<table>");
			outputBuilder.append("<thead><tr>");
			outputBuilder.append("<th>Holder name</th>");
			outputBuilder.append("<th>Account identifiers</th>");
			outputBuilder.append("<th>Account type</th>");
			outputBuilder.append("<th>Available balance</th>");
			outputBuilder.append("<th>Current balance</th>");
			outputBuilder.append("<th>Currency</th>");
			outputBuilder.append("</tr></thead>");

			outputBuilder.append("<tbody>");
			outputBuilder.append("<tr>");

			outputBuilder.append("<td>" + accountDetails.getAccountHolderName() + "</td>");

			outputBuilder.append("<td><ul>");
			for (AccountIdentifier identifier : accountDetails.getAccountIdentifiers()) {
				outputBuilder.append("<li>" + identifier.toString() + "</li>");
			}
			outputBuilder.append("</ul></td>");

			outputBuilder.append("<td>" + accountDetails.getType().name() + "</td>");
			outputBuilder.append("<td>" + balance.getAvailable().getValue() + "</td>");
			outputBuilder.append("<td>" + balance.getCurrent().getValue() + "</td>");
			outputBuilder.append("<td>" + balance.getAvailable().getCurrency() + "</td>");

			outputBuilder.append("</tr>");
			outputBuilder.append("</tbody></table>");

			fetchTransactions(client, account, accessToken, outputBuilder);
		}
	}

	private void fetchTransactions(TokenIoClient client, Account account, String accessToken, StringBuilder outputBuilder) {
		List<Transaction> transactions = client.getTransactions(account.getId(), null, 100, accessToken);

		outputBuilder.append("<h2>Transactions</h2>");

		outputBuilder.append("<table>");
		outputBuilder.append("<thead><tr>");
		outputBuilder.append("<th>ID</th>");
		outputBuilder.append("<th>Type</th>");
		outputBuilder.append("<th>Status</th>");
		outputBuilder.append("<th>Amount</th>");
		outputBuilder.append("<th>Currency</th>");
		outputBuilder.append("<th>Created at</th>");
		outputBuilder.append("<th>Creditor (account identifiers)</th>");
		outputBuilder.append("<th>Creditor (customer data)</th>");
		outputBuilder.append("</tr></thead>");

		outputBuilder.append("<tbody>");

		for (Transaction transaction : transactions) {
			outputBuilder.append("<tr>");

			outputBuilder.append("<td>" + transaction.getId() + "</td>");
			outputBuilder.append("<td>" + transaction.getType().name() + "</td>");
			outputBuilder.append("<td>" + transaction.getStatus().name() + "</td>");
			outputBuilder.append("<td>" + transaction.getAmount().getValue() + "</td>");
			outputBuilder.append("<td>" + transaction.getAmount().getCurrency() + "</td>");
			outputBuilder.append("<td>" + transaction.getCreatedAtMs() + "</td>");

			outputBuilder.append(
					"<td>" + Optional.ofNullable(transaction.getCreditorEndpoint().getAccountIdentifier()).map(Object::toString).orElse("") + "</td>");
			outputBuilder.append(
					"<td>" + Optional.ofNullable(transaction.getCreditorEndpoint().getCustomerData()).map(Object::toString).orElse("") + "</td>");

			outputBuilder.append("</tr>");
		}
		outputBuilder.append("</tbody></table>");
	}

	private Member getMember() {
		File keysDir = new File("./keys");
		String[] paths = keysDir.list();
		if (paths == null) {
			throw new IllegalStateException("Could not find ./keys folder");
		}
		return Arrays.stream(paths)
				.filter(path -> path.contains(config.getBankId() + ".json"))
				.map(path -> "./keys/" + path)
				.findFirst()
				.map(this::createMember)
				.orElseThrow(() -> new ConfigurationException("No " + config.getBankId() + ".json" + " configuration file"));
	}

	private Member createMember(String filePath) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject =  (JSONObject) obj;
			return Member.builder()
					.id((String) jsonObject.get("memberId"))
					.realmId((String) jsonObject.get("realmId"))
					.realm((String) jsonObject.get("realm"))
					.type((String) jsonObject.get("type"))
					.value((String) jsonObject.get("psuId"))
					.apiKey((String) jsonObject.get("apiKey"))
					.build();
		} catch (IOException | ParseException e) {
			throw new ConfigurationException("Error during parsing member configuration file");
		}
	}

	private static ApplicationConfig getAppConfig() {
		try (InputStream applicationPropertiesFile = Files.newInputStream(Paths.get("./src/main/resources/application.properties"))) {
			Properties properties = new Properties();
			properties.load(applicationPropertiesFile);
			return ApplicationConfig.builder()
					.port(Integer.parseInt(properties.getProperty("port")))
					.applicationBaseUrl(properties.getProperty("applicationBaseUrl"))
					.callbackUrl(properties.getProperty("callbackUrl"))
					.bankId(properties.getProperty("bankId"))
					.webApp(Boolean.parseBoolean(properties.getProperty("webApp")))
					.build();
		} catch (IOException e) {
			throw new ConfigurationException("Could not find application.properties file");
		}
	}
}
