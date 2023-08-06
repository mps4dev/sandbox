package pl.mps.tokenio.sandbox.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Money {
    private String currency;
    private String value;
}
