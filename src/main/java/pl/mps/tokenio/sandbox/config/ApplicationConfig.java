package pl.mps.tokenio.sandbox.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplicationConfig {
    private int port;
    private String applicationBaseUrl;
    private String callbackUrl;
    private String bankId;
    private boolean webApp;
}
