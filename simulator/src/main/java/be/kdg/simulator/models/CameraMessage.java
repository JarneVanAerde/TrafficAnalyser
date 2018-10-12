package be.kdg.simulator.models;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple POJO used to store information about a camera message.
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CameraMessage {
    private int id;
    private String licensePlate;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return String.format("Camera %d spotted car with license plate %s at %s local time",
                id, licensePlate, timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS")));
    }
}
