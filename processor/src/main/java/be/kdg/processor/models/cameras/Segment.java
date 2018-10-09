package be.kdg.processor.models.cameras;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Simple POJO used to store information about a segment
 * A given camera can be part of a segment.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int segmentId;
    @JsonProperty("connectedCameraId")
    private int secondCameraId;
    private int distance;
    private int speedLimit;

    @Override
    public String toString() {
        return String.format("Second Camera %d\n" +
                "Distance in segment %d\n" +
                "Max legal speedLimit %d",
                secondCameraId, distance, speedLimit);
    }
}
