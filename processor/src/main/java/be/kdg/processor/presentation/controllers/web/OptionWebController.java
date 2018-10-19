package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.models.options.Option;
import be.kdg.processor.presentation.dto.OptionDTO;
import be.kdg.processor.presentation.dto.OptionDTOS;
import be.kdg.processor.services.exceptions.ControllerException;
import be.kdg.processor.services.impl.modelservices.OptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * web controller used for the option related views
 */
@Controller
@RequestMapping("/option")
public class OptionWebController {
    private final OptionService optionService;
    private final ModelMapper modelMapper;

    @Autowired
    public OptionWebController(OptionService optionService, ModelMapper modelMapper) {
        this.optionService = optionService;
        this.modelMapper = modelMapper;
    }

    /**
     * @return all the options of the application
     * @throws ControllerException is handled by the ControllerExceptionHandler
     */
    @GetMapping("/settings")
    public ModelAndView showOptions() throws ControllerException {
        List<Option> options = optionService.getOptions().stream()
                .sorted(Comparator.comparing(Option::getKey))
                .collect(Collectors.toList());
        if (options.isEmpty())
            throw new ControllerException(getClass().getSimpleName() + ": Requested list was empty");

        OptionDTO[] optionDTOArray = modelMapper.map(options, OptionDTO[].class);
        OptionDTOS optionDTOS = new OptionDTOS();
        Arrays.stream(optionDTOArray).forEach(optionDTOS::addOptionDTO);
        return new ModelAndView("optionSettings", "optionsDTOS", optionDTOS);
    }

    /**
     * Alls options of the form are saved to the database.
     *
     * @param optionDTOS wrapper for optionDTO.
     * @return a redirect to show the options.
     */
    @PostMapping("/update")
    public ModelAndView updateOptions(@ModelAttribute OptionDTOS optionDTOS) {
        List<OptionDTO> optionDTOSArray = optionDTOS.getOptionDTOList();

        for (OptionDTO optionDTO : optionDTOSArray) {
            Option option = new Option(optionDTO.getKey(), optionDTO.getValue());
            optionService.saveOption(option);
        }

        return new ModelAndView("redirect:/option/settings");
    }
}
