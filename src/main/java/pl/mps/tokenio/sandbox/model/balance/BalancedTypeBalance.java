package pl.mps.tokenio.sandbox.model.balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.common.Money;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalancedTypeBalance {
    private Money amount;
    private String type;
    private String updatedAtMs;
}
