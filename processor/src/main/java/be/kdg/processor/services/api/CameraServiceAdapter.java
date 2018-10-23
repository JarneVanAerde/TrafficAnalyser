package be.kdg.processor.services.api;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.services.exceptions.ServiceException;

public interface CameraServiceAdapter {
    Camera get(int id) throws ServiceException;
}
