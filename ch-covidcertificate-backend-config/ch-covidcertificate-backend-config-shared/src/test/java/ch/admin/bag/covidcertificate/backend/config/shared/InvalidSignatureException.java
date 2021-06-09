package ch.admin.bag.covidcertificate.backend.config.shared;

public class InvalidSignatureException extends RuntimeException {

    public InvalidSignatureException(Throwable cause) {
        super(cause);
    }
}
