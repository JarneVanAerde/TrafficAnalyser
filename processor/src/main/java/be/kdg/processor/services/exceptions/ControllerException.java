package be.kdg.processor.services.exceptions;

/**
 * Used to throw generalized controller exceptions in the controllers
 */
public class ControllerException extends Exception {
    public ControllerException(String message) {
        super(message);
    }
}
