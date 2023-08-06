package pl.mps.tokenio.sandbox.client;

import pl.mps.tokenio.sandbox.exception.AuthenticationException;
import pl.mps.tokenio.sandbox.exception.ServiceUnavailableException;
import pl.mps.tokenio.sandbox.model.account.Account;
import pl.mps.tokenio.sandbox.model.account.AccountsResponse;
import pl.mps.tokenio.sandbox.model.balance.BalanceResponse;
import pl.mps.tokenio.sandbox.model.balance.BalancesResponse;
import pl.mps.tokenio.sandbox.model.bank.BankServicesStatus;
import pl.mps.tokenio.sandbox.model.bank.BankServicesStatusResponse;
import pl.mps.tokenio.sandbox.model.bank.BankStatus;
import pl.mps.tokenio.sandbox.model.bank.BankStatusResponse;
import pl.mps.tokenio.sandbox.model.bank.BanksResponse;
import pl.mps.tokenio.sandbox.model.common.Alias;
import pl.mps.tokenio.sandbox.member.Member;
import pl.mps.tokenio.sandbox.product.Product;
import pl.mps.tokenio.sandbox.request.AccessBody;
import pl.mps.tokenio.sandbox.request.RequestOptions;
import pl.mps.tokenio.sandbox.request.RequestPayload;
import pl.mps.tokenio.sandbox.request.RequestPayloadTo;
import pl.mps.tokenio.sandbox.request.StoreTokenRequestRequest;
import pl.mps.tokenio.sandbox.request.TokenRequestRequest;
import pl.mps.tokenio.sandbox.model.tokenrequest.TokenRequestResponse;
import pl.mps.tokenio.sandbox.model.tokenrequest.TokenRequestResultResponse;
import pl.mps.tokenio.sandbox.model.tokenrequest.TokenRequestResultStatus;
import pl.mps.tokenio.sandbox.model.transaction.Transaction;
import pl.mps.tokenio.sandbox.model.transaction.TransactionsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.ACCOUNTS_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.AUTHORIZATION_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.AUTH_BASE_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.BALANCES_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.BANK_AUTH_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.BANK_EXISTS_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.BANK_SERVICES_STATUS_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.BANK_STATUS_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.BASE_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.STORE_TOKEN_REQUEST;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.TOKEN_REQUESTS;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.TOKEN_REQUESTS_RESULT;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.TRANSACTIONS_URL;
import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.WEB_APP_BASE_URL;
import static pl.mps.tokenio.sandbox.utils.TokenIoUtils.generateNonce;

public class TokenIoClient extends StandardHttpClient {

    public TokenIoClient(String apiKey) {
        super(apiKey);
    }

    public String getTokenRequestUrl(Member member, String bankId, String redirectUri, boolean webapp) {
        String tokenRequestId = getTokenRequestId(member, bankId, redirectUri);
        TokenRequestResultStatus tokenRequestIdStatus = getTokenRequestsResult(tokenRequestId);
        if (tokenRequestIdStatus == TokenRequestResultStatus.PENDING) {
            storeTokenRequest(tokenRequestId, bankId);
            if (webapp) {
                return getWebAppBankAuthUrl(tokenRequestId);
            } else {
                return getBankAuthUrl(member.getId(), bankId, tokenRequestId, redirectUri);
            }
        }
        throw new AuthenticationException("Cannot obtain valid request token");
    }

    private String getTokenRequestId(Member member, String bankId, String redirectUri) {
        List<String> types = new ArrayList<>();
        types.add("ACCOUNTS");
        types.add("BALANCES");
        types.add("TRANSACTIONS");
        TokenRequestRequest payload = TokenRequestRequest.builder()
            .requestOptions(RequestOptions.builder().bankId(bankId).build())
            .requestPayload(RequestPayload.builder()
                    .redirectUrl(redirectUri)
                    .refId(generateNonce())
                    .to(RequestPayloadTo.builder().alias(Alias.builder().realmId(member.getRealmId()).realm(member.getRealm()).type(member.getType()).value(member.getValue()).build()).id(member.getId()).build())
                    .accessBody(AccessBody.builder().type(types).build()).build())
            .build();
        TokenRequestResponse response = post(BASE_URL + TOKEN_REQUESTS, payload, TokenRequestResponse.class);
        return response.getTokenRequest().getId();
    }

    public void storeTokenRequest(String tokenRequestId, String bankId) {
        String url = BASE_URL + STORE_TOKEN_REQUEST.replace("{tokenRequestId}", tokenRequestId);
        StoreTokenRequestRequest payload = StoreTokenRequestRequest.builder().requestId(tokenRequestId).requestOptions(RequestOptions.builder().bankId(bankId).build()).build();
        put(url, payload, Void.class);
    }

    private TokenRequestResultStatus getTokenRequestsResult(String tokenRequestId) {
        String url = BASE_URL + TOKEN_REQUESTS_RESULT.replace("{tokenRequestId}", tokenRequestId);
        TokenRequestResultResponse response = get(url, TokenRequestResultResponse.class);
        return response.getStatus();
    }

    private String getBankAuthUrl(String memberId, String bankId, String requestId, String redirectUri) {
        return AUTH_BASE_URL + BANK_AUTH_URL.replace("{memberId}", memberId).replace("{bankId}", bankId).replace("{requestId}", requestId).replace("{redirectUri}", redirectUri);
    }

    private String getWebAppBankAuthUrl(String requestId) {
        return WEB_APP_BASE_URL + "/request-token/" + requestId;
    }

    private String getAuthorizationRedirectUrl(String tokenRequestId) {
        return BASE_URL + AUTHORIZATION_URL.replace("{tokenRequestId}", tokenRequestId);
    }

    @SuppressWarnings("unchecked")
    public void checkIfBankAvailableForMember(String bankId, String memberId) {
        String url = BASE_URL + BANK_EXISTS_URL.replace("{bankId}", bankId).replace("{memberId}", memberId);
        BanksResponse response = get(url, BanksResponse.class);
        boolean match = response.getBanks().stream().anyMatch(bank -> bankId.equals(bank.getId()));
        if (!match) {
            throw new ServiceUnavailableException(bankId);
        }
    }

    public boolean checkIfBankIsAvailable(String bankId, Product product) {
        String url = BASE_URL + BANK_STATUS_URL.replace("{bankId}", bankId).replace("{product}", product.name());
        BankStatusResponse response = get(url, BankStatusResponse.class);
        return BankStatus.AVAILABLE == response.getStatus();
    }

    public BankServicesStatus getBankServicesStatus(String bankId) {
        String url = BASE_URL + BANK_SERVICES_STATUS_URL.replace("{bankId}", bankId);
        BankServicesStatusResponse response = get(url, BankServicesStatusResponse.class);
        return response.getBankStatus();
    }

    public List<Account> getAccounts(String accessToken) {
        String url = BASE_URL + ACCOUNTS_URL;
        AccountsResponse response = get(url, accessToken, AccountsResponse.class);
        return response.getAccounts();
    }

    public List<BalanceResponse> getAccountBalances(String accountId, String accessToken) {
        String url = BASE_URL + BALANCES_URL.replace("{accountId}", accountId);
        BalancesResponse response = get(url, accessToken, BalancesResponse.class);
        return response.getResponse();
    }

    public List<Transaction> getTransactions(String accountId, Integer offset, Integer limit, String accessToken) {
        String url = BASE_URL + TRANSACTIONS_URL.replace("{accountId}", accountId).replace("{limit}", limit != null ? limit.toString() : "199");
        if (offset != null) {
            url = url.replace("{offset}", offset.toString());
        } else {
            url = url.replace("page.offset={offset}&", "");
        }
        TransactionsResponse response = get(url, accessToken, TransactionsResponse.class);
        return response.getTransactions();
    }
}
