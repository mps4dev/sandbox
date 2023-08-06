package pl.mps.tokenio.sandbox.model.account.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlusgiroIdentifier implements AccountIdentifier {
    private AccountIdentifierPlusgiro plusgiro;

    @Override
    public String getIdentifier() {
        return "plusgiro: [" + plusgiro.getPlusgiroNumber() + "]";
    }
}
