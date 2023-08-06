package pl.mps.tokenio.sandbox.model.account.identifier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenIdentifier implements AccountIdentifier {
    private AccountIdentifierToken token;

    @Override
    public String getIdentifier() {
        return "token: [accountId: " + token.getAccountId() + ", memberId: " + token.getMemberId() + "]";
    }
}
