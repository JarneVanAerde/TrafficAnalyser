package be.kdg.processor.models.cameras;

import lombok.*;

/**
 * Simple POJO used to store information about a segment
 * A given camera can be part of a segment.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Segment {
    private int connectedCameraId;
    private int distance;
    private int speedLimit;

    @Override
    public String toString() {
        return String.format("Second Camera %d\n" +
                "Distance in segment %d\n" +
                "Max legal speedLimit %d",
                connectedCameraId, distance, speedLimit);
    }
}
