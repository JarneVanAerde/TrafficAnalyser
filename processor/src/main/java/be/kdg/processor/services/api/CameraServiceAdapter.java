package be.kdg.processor.services.api;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.sa.services.CameraNotFoundException;

import java.io.IOException;

public interface CameraServiceAdapter {
    Camera get(int id) throws IOException, CameraNotFoundException, ServiceException;
}
