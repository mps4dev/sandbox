package pl.mps.tokenio.sandbox.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
    private String clientId;
}
