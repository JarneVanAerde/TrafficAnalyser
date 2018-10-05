package be.kdg.processor.receivers;

import java.io.IOException;
import java.util.List;

public interface Receiver<T> {
    void receiveMessage(String message) throws IOException;
    List<T> getBufferdObjects();
}
