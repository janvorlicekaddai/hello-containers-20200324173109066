package cz.addai.web.interceptor;

public class EInvalidClientIdException extends RuntimeException {

    public EInvalidClientIdException(String clientId) {
        super("ClientId " + clientId + " is not valid.");
    }
}
