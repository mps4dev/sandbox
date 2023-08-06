package pl.mps.tokenio.sandbox.model.tokenrequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.common.Alias;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenMember {
    private Alias alias;
    private String id;
}
