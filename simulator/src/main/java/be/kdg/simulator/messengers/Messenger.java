package be.kdg.simulator.messengers;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Messenger {
    void sendMessage() throws JsonProcessingException;
}
