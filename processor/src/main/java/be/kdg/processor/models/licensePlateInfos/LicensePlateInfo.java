package be.kdg.processor.models.licensePlateInfos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LicensePlateInfo {
    private String plateId;
    private String nationalNumber;
    private int euroNumber;

    @Override
    public String toString() {
        return String.format("Plate: %s\nNational number: %s\nEuro standard: %d",
                plateId, nationalNumber, euroNumber);
    }
}
