package pl.mps.tokenio.sandbox.request;

import lombok.Builder;
import lombok.Data;
import pl.mps.tokenio.sandbox.model.common.Alias;

@Data
@Builder
public class RequestPayloadTo {
    private Alias alias;
    private String id;
}
