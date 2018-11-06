package be.kdg.processor.models.cameras;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple POJO used to store information about a camera message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CameraMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int messageId;
    private int cameraId;
    private String licensePlate;
    private LocalDateTime timestamp;

    public CameraMessage(int cameraId, String licensePlate, LocalDateTime timestamp) {
        this.cameraId = cameraId;
        this.licensePlate = licensePlate;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("Camera %d spotted car with license plate %s at %s local time",
                cameraId, licensePlate, timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS")));
    }
}

