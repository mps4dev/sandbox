package pl.mps.tokenio.sandbox.member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {
    private String id;
    private String realmId;
    private String partnerId;
    private String realm;
    private String type;
    private String value;
    private String apiKey;
}
