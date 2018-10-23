package be.kdg.simulator.services.exceptions;

/**
 * wrapper exception that is used for all exceptions
 * throughout the application.
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}
