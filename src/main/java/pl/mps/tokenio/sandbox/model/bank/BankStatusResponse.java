package pl.mps.tokenio.sandbox.model.bank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankStatusResponse {
    private BankStatus status;
}
