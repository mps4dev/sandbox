package pl.mps.tokenio.sandbox.model.account;

import lombok.Data;

import java.util.List;

@Data
public class AccountsResponse {
    private List<Account> accounts;
}
