package be.kdg.processor.presentation.dto;

import be.kdg.processor.models.fines.FineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FineDTO {
    private int fineId;
    private double amount;
    private FineType fineType;
    private LocalDateTime creationDate;

    //EMISSION SPECIFIC
    private int vehicleEuroNorm;
    private int legalEuroNorm;

    //SPEED SPECIFIC
    private double vehicleSpeed;
    private double legalSpeed;
}
