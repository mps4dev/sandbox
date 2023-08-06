package pl.mps.tokenio.sandbox.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestOptions {
    private String bankId;
}
