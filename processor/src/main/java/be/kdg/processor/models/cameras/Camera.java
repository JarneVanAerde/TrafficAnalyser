package be.kdg.processor.models.cameras;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Camera {
    private int id;
    private double locationLat;
    private double locationLong;
    private int secondCamera;
    private int distance;
    private int speed;
    private int euroNorm;
    private final List<CameraMessage> cameraMessages;

    public Camera(int id, double locationLat, double locationLong, int secondCamera, int distance, int speed, int euroNorm) {
        this.id = id;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.secondCamera = secondCamera;
        this.distance = distance;
        this.speed = speed;
        this.euroNorm = euroNorm;
        this.cameraMessages = new ArrayList<>();
    }

    public Camera() {
        this.cameraMessages = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Camera{" +
                "id=" + id +
                ", locationLat=" + locationLat +
                ", locationLong=" + locationLong +
                ", secondCamera=" + secondCamera +
                ", distance=" + distance +
                ", speed=" + speed +
                ", euroNorm=" + euroNorm +
                '}';
    }
}
