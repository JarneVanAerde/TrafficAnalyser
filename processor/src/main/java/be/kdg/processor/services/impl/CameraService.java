package be.kdg.processor.services.impl;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.services.api.CameraServiceAdapter;
import be.kdg.sa.services.CameraServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraService implements CameraServiceAdapter {
    private final CameraServiceProxy cameraServiceProxy;

    @Autowired
    public CameraService(CameraServiceProxy cameraServiceProxy) {
        this.cameraServiceProxy = cameraServiceProxy;
    }

    @Override
    public Camera get(int id) {
        return null;
    }
}
