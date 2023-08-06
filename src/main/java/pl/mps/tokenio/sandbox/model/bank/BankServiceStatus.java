package pl.mps.tokenio.sandbox.model.bank;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BankServiceStatus {
    LIVE("LIVE"), DOWN("DOWN"), NO_CALLS("NO CALLS");

    private final String status;

    BankServiceStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
