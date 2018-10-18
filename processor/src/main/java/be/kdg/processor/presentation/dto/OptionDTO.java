package be.kdg.processor.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionDTO {
    @NotNull
    private String key;
    @NotNull
    private double value;
}
