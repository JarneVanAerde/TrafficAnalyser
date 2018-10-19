package be.kdg.processor.presentation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class OptionDTOS {
    private List<OptionDTO> optionDTOList;

    public OptionDTOS() {
        optionDTOList = new ArrayList<>();
    }

    public void addOptionDTO(OptionDTO optionDTO) {
        optionDTOList.add(optionDTO);
    }
}
