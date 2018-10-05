package be.kdg.processor.services.api;

import be.kdg.processor.models.cameras.Camera;

public interface CameraServiceAdapter {
    Camera get(int id);
}
