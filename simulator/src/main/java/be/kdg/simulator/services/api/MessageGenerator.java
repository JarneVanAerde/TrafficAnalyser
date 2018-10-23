package be.kdg.simulator.services.api;

import be.kdg.simulator.models.CameraMessage;
import be.kdg.simulator.services.exceptions.ServiceException;

public interface MessageGenerator {
     CameraMessage generate() throws ServiceException;
}
