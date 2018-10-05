package be.kdg.processor.persistence;

import be.kdg.processor.models.cameras.Camera;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CameraRepository implements RepositoryCRUD<Camera> {
    private final List<Camera> cameras;

    public CameraRepository() {
        this.cameras = new ArrayList<>();
    }

    @Override
    public Camera create(Camera camera) {
        cameras.add(camera);
        return camera;
    }

    @Override
    public void delete(Camera camera) {
        cameras.remove(camera);
    }

    @Override
    public Camera read(int id) {
        return cameras.get(id);
    }

    @Override
    public Camera update(Camera camera) {
        return null;
    }
}
