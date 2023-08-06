package pl.mps.tokenio.sandbox.model.tokenrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequestOptionsTokenInternal {
    private String redirectUrl;
    private boolean usingWebApp;
}
