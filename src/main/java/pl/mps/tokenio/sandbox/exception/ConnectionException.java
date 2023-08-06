package pl.mps.tokenio.sandbox.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException(String method, String url, Class clazz) {
        super(String.format("Cannot perform %s request - url: %s for %s", method, url, clazz.getSimpleName()));
    }
}
