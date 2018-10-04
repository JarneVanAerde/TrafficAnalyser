package be.kdg.processor.models.cameras;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Simple POJO used to store the location of a specific camera.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("long")
    private double longitude;

    @Override
    public String toString() {
        return String.format("Location:\n\tLatitude %.2f\n\tLongitude %.2f",
                latitude, longitude);
    }
}
