package pl.mps.tokenio.sandbox.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.account.identifier.AccountIdentifier;
import pl.mps.tokenio.sandbox.model.common.CustomerDataCreditor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class TransferCreditorEndpoint {
    private AccountIdentifier accountIdentifier;
    private CustomerDataCreditor customerData;
}
