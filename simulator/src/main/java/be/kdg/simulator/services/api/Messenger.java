package be.kdg.simulator.services.api;

import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.exceptions.ServiceException;

public interface Messenger {
    void sendMessage(CameraMessage message) throws ServiceException;
}
