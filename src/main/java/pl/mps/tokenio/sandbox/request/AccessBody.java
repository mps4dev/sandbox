package pl.mps.tokenio.sandbox.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccessBody {
    private List<String> type;
}
