package be.kdg.processor.presentation.dto;

import be.kdg.processor.models.options.OptionKey;

import javax.validation.constraints.NotNull;

public class OptionDTO {
    @NotNull
    private OptionKey key;
    @NotNull
    private double value;
}
