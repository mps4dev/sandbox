package pl.mps.tokenio.sandbox.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.account.identifier.AccountIdentifier;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {
    private String accountHolderName;
    private String identifier;
    private List<AccountIdentifier> accountIdentifiers = new ArrayList<>();
    private String status;
    private AccountType type;
    private Metadata metadata;
}
