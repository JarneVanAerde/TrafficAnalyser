package be.kdg.processor.services.exceptions;

/**
 * Used to throw generalized service exceptions throughout the application.
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}
