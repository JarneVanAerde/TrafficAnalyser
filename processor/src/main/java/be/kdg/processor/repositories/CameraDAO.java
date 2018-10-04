package be.kdg.processor.repositories;

import be.kdg.processor.models.cameras.Camera;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CameraDAO implements RepositoryCRUD<Camera> {
    private final List<Camera> cameras;

    public CameraDAO() {
        this.cameras = new ArrayList<>();
    }

    @Override
    public void add(Camera camera) {
        cameras.add(camera);
    }

    @Override
    public void remove(Camera camera) {
        cameras.remove(camera);
    }

    @Override
    public Camera get(int id) {
        return cameras.get(id);
    }

    @Override
    public Camera update(Camera camera) {
        Camera cameraToUpdate = cameras.get(camera.getCameraId());
        cameraToUpdate = camera;
        return cameraToUpdate;
    }
}
