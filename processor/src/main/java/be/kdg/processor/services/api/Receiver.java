package be.kdg.processor.services.api;

import java.io.IOException;
import java.util.List;

public interface Receiver<T> {
    void receiveMessage(String message) throws IOException;
}
