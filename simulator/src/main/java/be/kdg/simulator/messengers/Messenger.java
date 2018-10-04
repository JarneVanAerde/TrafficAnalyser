package be.kdg.simulator.messengers;

import be.kdg.simulator.model.CameraMessage;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface Messenger {
    void sendMessage(CameraMessage message) throws JsonProcessingException;
}
