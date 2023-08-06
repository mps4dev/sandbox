package pl.mps.tokenio.sandbox.model.bank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankWithGroup {
    private String id;
    private String identifier;
    private String name;
    private String bankGroup;
    private String bic;
    private String bankCode;
}
