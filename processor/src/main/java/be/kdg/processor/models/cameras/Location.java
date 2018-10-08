package be.kdg.processor.models.cameras;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Simple POJO used to store the location of a specific camera.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int locationId;
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
