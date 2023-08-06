package pl.mps.tokenio.sandbox.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.common.Money;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private String id;
    private Money amount;
    private TransactionType type;
    private TransactionStatus status;
    private String createdAtMs;
    private TransferCreditorEndpoint creditorEndpoint;
}