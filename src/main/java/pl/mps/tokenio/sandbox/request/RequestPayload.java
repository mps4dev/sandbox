package pl.mps.tokenio.sandbox.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPayload {
    private String redirectUrl;
    private String refId;
    private RequestPayloadTo to;
    private AccessBody accessBody;
}
