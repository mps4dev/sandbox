package pl.mps.tokenio.sandbox.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountFeatures {
    private boolean supportsInformation;
    private boolean supportsReceivePayment;
    private boolean supportsSendPayment;
}
