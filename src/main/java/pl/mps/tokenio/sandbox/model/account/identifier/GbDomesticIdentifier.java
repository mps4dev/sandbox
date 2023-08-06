package pl.mps.tokenio.sandbox.model.account.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GbDomesticIdentifier implements AccountIdentifier {
    private AccountIdentifierGbDomestic gbDomestic;

    @Override
    public String getIdentifier() {
        return "gbDomestic: [accountNUmber: " + gbDomestic.getAccountNumber() + ", sortCode" + gbDomestic.getSortCode() + "]";
    }
}
