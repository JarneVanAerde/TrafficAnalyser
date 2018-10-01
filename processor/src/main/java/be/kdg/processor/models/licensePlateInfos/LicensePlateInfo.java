package be.kdg.processor.models.licensePlateInfos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LicensePlateInfo {
    private String plate;
    private String nationalNumber;
    private int euroNumber;

    @Override
    public String toString() {
        return "LicensePlateInfo{" +
                "plate='" + plate + '\'' +
                ", nationalNumber='" + nationalNumber + '\'' +
                ", euroNumber=" + euroNumber +
                '}';
    }
}
