package pl.mps.tokenio.sandbox.model.balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.common.RequestStatus;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceResponse {
    private Balance balance;
    private RequestStatus status;
}
