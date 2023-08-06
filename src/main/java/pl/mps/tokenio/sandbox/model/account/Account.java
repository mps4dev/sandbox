package pl.mps.tokenio.sandbox.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private String id;
    private AccountDetails accountDetails;
    private AccountFeatures accountFeatures;
    private String bankId;
    private String name;
}
