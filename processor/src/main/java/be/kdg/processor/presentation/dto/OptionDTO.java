package be.kdg.processor.presentation.dto;

import be.kdg.processor.models.options.OptionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionDTO {
    @NotNull
    private OptionKey key;
    @NotNull
    private double value;
}
