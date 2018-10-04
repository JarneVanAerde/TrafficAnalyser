package be.kdg.processor.models.cameras;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Camera {
    private int cameraId;
    private int euroNorm;
    private Segment segment;
    private Location location;
    private final List<CameraMessage> cameraMessages;

    public Camera(int id, int euroNorm, Segment segment, Location location) {
        this.cameraId = id;
        this.euroNorm = euroNorm;
        this.segment = segment;
        this.location = location;
        this.cameraMessages = new ArrayList<>();
    }

    public Camera(int id,  Segment segment, Location location) {
        this.cameraId = id;
        this.euroNorm = -1;
        this.segment = segment;
        this.location = location;
        this.cameraMessages = new ArrayList<>();
    }

    public Camera() {
        this.cameraMessages = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Camera %d\n" +
                "%s\n" +
                "%s\n" +
                "euroNorm %d",
                cameraId, location, segment, euroNorm);
    }
}
