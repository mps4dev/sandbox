package pl.mps.tokenio.sandbox.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.common.RequestStatus;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionsResponse {
    private String offset;
    private RequestStatus status;
    List<Transaction> transactions;

}
