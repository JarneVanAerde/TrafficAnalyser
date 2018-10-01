package be.kdg.processor.models.cameras;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CameraInfo {
    private int id;
    private double locationLat;
    private double locationLong;
    private int secondCamera;
    private int distance;
    private int speed;
    private int euroNorm;

    @Override
    public String toString() {
        return "CameraInfo{" +
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
