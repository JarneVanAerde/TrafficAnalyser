package be.kdg.processor.services.impl.adapters;

import be.kdg.processor.models.cameras.Camera;
import be.kdg.processor.services.api.CameraServiceAdapter;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * @param id for the requested camera
     * @return a camera from the proxy service
     * @throws ServiceException wrapper-exception
     */
    @Cacheable(value = "cameras")
    @Override
    public Camera get(int id) throws ServiceException {
        try {
            String cameraJson = cameraServiceProxy.get(id);
            return objectMapper.readValue(cameraJson, Camera.class);
        } catch (IOException | CameraNotFoundException e) {
           throw new ServiceException(getClass().getSimpleName() + ": " + e.getMessage());
        }

    }
}
