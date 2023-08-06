package pl.mps.tokenio.sandbox.model.balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.common.Money;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Balance {
    private String accountId;
    private Money available;
    private Money current;
    private List<BalancedTypeBalance> otherBalances;
}
