package pl.mps.tokenio.sandbox.model.tokenrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequestOptions {
    private String bankId;
    private TokenMember from;
    private String psuId;
    private boolean receiptRequested;
    private TokenRequestOptionsTokenInternal tokenInternal;
}
