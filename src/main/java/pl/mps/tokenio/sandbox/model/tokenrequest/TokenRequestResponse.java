package pl.mps.tokenio.sandbox.model.tokenrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequestResponse {
    private TokenRequest tokenRequest;
}
