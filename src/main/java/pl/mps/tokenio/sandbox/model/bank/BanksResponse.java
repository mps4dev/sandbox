package pl.mps.tokenio.sandbox.model.bank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BanksResponse {
    private int rank;
    private String bankSubGroup;
    private List<BankWithGroup> banks;

}
