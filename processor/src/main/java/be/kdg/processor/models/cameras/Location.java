package be.kdg.processor.models.cameras;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Location {
    @JsonProperty("lat")
    private double locationLat;
    @JsonProperty("long")
    private double locationLong;

    @Override
    public String toString() {
        return String.format("Location:\n\tLatitude %.2f\n\tLongitude %.2f",
                locationLat, locationLong);
    }
}
