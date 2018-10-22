package be.kdg.processor.presentation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FineDTOS {
    private List<FineDTO> fineDTOList;

    public FineDTOS() {
        fineDTOList = new ArrayList<>();
    }

    public void addFineDTO(FineDTO fineDTO) {
        fineDTOList.add(fineDTO);
    }
}
