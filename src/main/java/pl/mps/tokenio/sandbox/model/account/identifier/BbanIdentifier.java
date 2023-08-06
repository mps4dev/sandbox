package pl.mps.tokenio.sandbox.model.account.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BbanIdentifier implements AccountIdentifier {
    private AccountIdentifierBban bban;

    @Override
    public String getIdentifier() {
        return "bban: [" + bban.getBban() + ", clearingNumber: " + bban.getClearingNumber() + "]";
    }
}
