package pl.mps.tokenio.sandbox.model.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Alias {
    private String type;
    private String value;
    private String realm;
    private String realmId;
}
