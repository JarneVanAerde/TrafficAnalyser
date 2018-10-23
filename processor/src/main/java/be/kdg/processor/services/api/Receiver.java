package be.kdg.processor.services.api;

import java.io.IOException;

public interface Receiver<T> {
    void receiveMessage(String message) throws IOException;
}
