package pl.mps.tokenio.sandbox.utils;

import lombok.experimental.UtilityClass;
import org.bitcoinj.core.Base58;

import java.security.SecureRandom;

import static pl.mps.tokenio.sandbox.client.TokenIoClientConstants.Urls.NONCE_NUM_BYTES;

@UtilityClass
public class TokenIoUtils {

    public static String generateNonce() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[NONCE_NUM_BYTES];
        random.nextBytes(bytes);
        return Base58.encode(bytes);
    }
}
