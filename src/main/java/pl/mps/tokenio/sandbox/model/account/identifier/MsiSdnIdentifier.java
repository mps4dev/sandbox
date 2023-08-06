package pl.mps.tokenio.sandbox.model.account.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MsiSdnIdentifier implements AccountIdentifier {
    private AccountIdentifierMsisdn msisdn;

    @Override
    public String getIdentifier() {
        return "msisdn: [" + msisdn.getMsisdn() + "]";
    }
}
