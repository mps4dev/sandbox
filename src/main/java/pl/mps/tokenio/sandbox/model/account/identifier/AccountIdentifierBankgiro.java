package pl.mps.tokenio.sandbox.model.account.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountIdentifierBankgiro {
    private String bankgiroNumber;
}
