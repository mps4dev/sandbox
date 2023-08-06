package pl.mps.tokenio.sandbox.client;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenIoClientConstants {
    
    @UtilityClass
    public class Urls {
        public static final int NONCE_NUM_BYTES = 12;
        public static final String CALLBACK = "https://web-app.sandbox.token.io/app/auth/callback";

        public static final String WEB_APP_BASE_URL = "https://web-app.sandbox.token.io/app";

        public static final String BASE_URL = "https://api.sandbox.token.io";

        public static final String AUTH_BASE_URL = "https://auth.sandbox.token.io";
        public static final String TOKEN_REQUESTS = "/token-requests";

        public static final String STORE_TOKEN_REQUEST = "/token-requests/{tokenRequestId}";
        public static final String TOKEN_REQUESTS_RESULT = "/token-requests/{tokenRequestId}/result";

        public static final String AUTHORIZATION_URL = "/request-token/{tokenRequestId}";

        public static final String BANK_EXISTS_URL = "/banks?ids={bankId}&sort=rank&perPage=25&memberId={memberId}&bank_features.supports_information.value=true&";
        public static final String BANK_STATUS_URL = "/bank-information/banks/{bankId}/outage-status?ProductType={product}";

        public static final String BANK_SERVICES_STATUS_URL = "/reports/banks/{bankId}/status";

        public static final String BANK_AUTH_URL = "/banklink?bank_id={bankId}&member_id={memberId}&redirect-uri={redirectUri}&request-id={requestId}";

        public static final String ACCOUNTS_URL = "/accounts";
        public static final String BALANCES_URL = "/account-balance?accountId={accountId}";

        public static final String TRANSACTIONS_URL = "/accounts/{accountId}/transactions?page.offset={offset}&page.limit={limit}";
    }

    @UtilityClass
    public class Headers {
        public static final String AUTHORIZATION = "Authorization";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String ACCESS_TOKEN = "on-behalf-of";
    }
}
