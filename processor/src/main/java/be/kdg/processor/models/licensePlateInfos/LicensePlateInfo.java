package be.kdg.processor.models.licensePlateInfos;

import lombok.*;

/**
 * This class is used to store basic information about license plates.
 * Information about a license plate comes form the external service LicensePlateServiceProxy.
 */
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
