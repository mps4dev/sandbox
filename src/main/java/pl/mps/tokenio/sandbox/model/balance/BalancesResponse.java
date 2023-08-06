package pl.mps.tokenio.sandbox.model.balance;

import lombok.Data;

import java.util.List;

@Data
public class BalancesResponse {
    private List<BalanceResponse> response;
}
