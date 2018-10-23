package be.kdg.processor.presentation.dto;

import be.kdg.processor.models.fines.FineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * FineDTO is used for transferring information about emission fines and speed fines.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FineDTO {
    @NotNull
    private int fineId;
    @NotNull
    private double amount;
    private FineType fineType;
    private LocalDateTime creationDate;
    @NotNull
    private String motivation;
    private boolean approved;
    private String plateId;

    //EMISSION SPECIFIC
    private int vehicleEuroNorm;
    private int legalEuroNorm;

    //SPEED SPECIFIC
    private double vehicleSpeed;
    private double legalSpeed;
}
