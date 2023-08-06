package pl.mps.tokenio.sandbox.model.tokenrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequest {
    private String id;
    private TokenRequestOptions requestOptions;
    private TokenRequestResultStatus status;

    private String statusReasonInformation;

}
