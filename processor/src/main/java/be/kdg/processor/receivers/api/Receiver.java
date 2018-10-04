package be.kdg.processor.receivers.api;

import java.io.IOException;
import java.util.List;

public interface Receiver<E> {
    void receiveMessage(String message) throws IOException;
    List<E> getBufferdObjects();
}
