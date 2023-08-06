package pl.mps.tokenio.sandbox.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreTokenRequestRequest {
    private String requestId;
    private RequestOptions requestOptions;
}
