package pl.mps.tokenio.sandbox.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDataCreditor {
    private AddressInfo address;
    List<String> legalNames;

    @Override
    public String toString() {
        return "Names=" + legalNames + ", address=" + address;
    }
}
