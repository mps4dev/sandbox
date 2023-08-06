package pl.mps.tokenio.sandbox.model.account.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IbanIdentifier implements AccountIdentifier {
    private AccountIdentifierIban iban;

    @Override
    public String getIdentifier() {
        return "iban: [" + iban.getIban() + "]";
    }
}
