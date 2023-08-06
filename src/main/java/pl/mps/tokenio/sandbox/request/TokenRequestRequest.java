package pl.mps.tokenio.sandbox.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenRequestRequest {
    private RequestOptions requestOptions;
    private RequestPayload requestPayload;
}
