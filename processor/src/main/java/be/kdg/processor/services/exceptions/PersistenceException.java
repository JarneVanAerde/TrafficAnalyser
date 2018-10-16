package be.kdg.processor.services.exceptions;

import java.util.function.Supplier;

public class PersistenceException extends Exception {
    public PersistenceException(String message) {
        super(message);
    }
}
