package be.kdg.processor.models.licensePlateInfos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LicensePlateInfo {
    private String plate;
    private String nationalNumber;
    private int euroNumber;

    @Override
    public String toString() {
        return String.format("Plate: %s\nNational number: %s\nEuro standard: %d",
                plate, nationalNumber, euroNumber);
    }
}
