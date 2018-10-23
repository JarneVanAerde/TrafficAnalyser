package be.kdg.processor.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * OptionDTO is used for transferring option information.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionDTO {
    @NotNull
    private String key;
    @NotNull
    private double value;
}
