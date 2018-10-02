package be.kdg.processor.models.cameras;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Segment {
    @JsonProperty("connectedCameraId")
    private int secondCamera;
    private int distance;
    private int speedLimit;

    @Override
    public String toString() {
        return String.format("Second Camera %d\n" +
                "Distance in segment %d\n" +
                "Max legal speedLimit %d",
                secondCamera, distance, speedLimit);
    }
}
