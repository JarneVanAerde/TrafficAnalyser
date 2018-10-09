package be.kdg.processor.services.impl.adapters;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.services.api.CameraServiceAdapter;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Adapter used for the CameraServiceProxy.
 */
@Service
public class CameraInfoService implements CameraServiceAdapter {
    private final CameraServiceProxy cameraServiceProxy;
    private final ObjectMapper objectMapper;

    @Autowired
    public CameraInfoService(CameraServiceProxy cameraServiceProxy, ObjectMapper objectMapper) {
        this.cameraServiceProxy = cameraServiceProxy;
        this.objectMapper = objectMapper;
    }

    @Override
    public Camera get(int id) throws IOException, CameraNotFoundException {
        String cameraJson = cameraServiceProxy.get(id);
        return objectMapper.readValue(cameraJson, Camera.class);
    }
}
