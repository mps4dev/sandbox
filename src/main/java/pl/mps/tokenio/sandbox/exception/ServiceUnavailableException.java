package pl.mps.tokenio.sandbox.exception;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String bankId) {
        super(String.format("Bank %s not available", bankId));
    }
}
