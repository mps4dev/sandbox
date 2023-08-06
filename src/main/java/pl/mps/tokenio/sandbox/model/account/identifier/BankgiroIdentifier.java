package pl.mps.tokenio.sandbox.model.account.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class BankgiroIdentifier implements AccountIdentifier {
    private AccountIdentifierBankgiro bankgiro;

    @Override
    public String getIdentifier() {
        return "bangiro: [" + bankgiro.getBankgiroNumber() + "]";
    }
}
