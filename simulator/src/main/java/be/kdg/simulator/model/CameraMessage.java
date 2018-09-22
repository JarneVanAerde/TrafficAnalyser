package be.kdg.simulator.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class CameraMessage {
    private int id;
    private String licensePlate;
    private LocalDateTime timestamp;

    public CameraMessage() {
    }

    public CameraMessage(int id, String licensePlate, LocalDateTime timestamp) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("Camera %d spotted car with license plate %s at %s local time", id, licensePlate, DateTimeFormatter.ofPattern("HH:mm:ss:SS").format(timestamp));
    }
}
