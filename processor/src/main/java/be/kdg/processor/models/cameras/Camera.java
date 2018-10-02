package be.kdg.processor.models.cameras;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Camera {
    @JsonProperty("cameraId")
    private int id;
    private int[] location;
    @JsonProperty("lat")
    private double locationLat;
    @JsonProperty("long")
    private double locationLong;
    @JsonProperty("connectedCameraId")
    private int secondCamera;
    private int distance;
    private int speedLimit;
    private int euroNorm;
    private final List<CameraMessage> cameraMessages;

    public Camera(int id, double locationLat, double locationLong, int secondCamera, int distance, int speed, int euroNorm) {
        this.id = id;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.secondCamera = secondCamera;
        this.distance = distance;
        this.speedLimit = speed;
        this.euroNorm = euroNorm;
        this.cameraMessages = new ArrayList<>();
    }

    public Camera() {
        this.cameraMessages = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Camera %d" +
                "\nLocation:\n\tLatitude %.2f\n\tLongitude %.2f" +
                "Second Camera %d" +
                "Distance in segment %d" +
                "Max legal speedLimit %d" +
                "euroNorm %d",
                id, locationLat, locationLong, secondCamera,
                distance, speedLimit, euroNorm);
    }
}
