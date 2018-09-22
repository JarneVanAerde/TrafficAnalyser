package be.kdg.simulator.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class CameraMessage {
    private int id;
    private String licensePlate;
    private LocalDateTime timestamp;

    public CameraMessage(int id, String licensePlate, LocalDateTime timestamp) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("Camera %d spotted car with license plate %s at %s local time", id, licensePlate, timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss:SSS")));
    }
}
