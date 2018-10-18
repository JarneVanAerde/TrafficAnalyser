package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.models.options.Option;
import be.kdg.processor.models.options.OptionKey;
import be.kdg.processor.presentation.dto.OptionDTO;
import be.kdg.processor.services.exceptions.ControllerException;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.modelservices.OptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/settings")
    public ModelAndView showOptions() throws ServiceException {
        /*List<Option> options = optionService.getOptions();
        if (options.isEmpty())
            throw new ControllerException(getClass().getSimpleName() + ": Requested list was empty");

        OptionDTO[] optionDTO = modelMapper.map(options, OptionDTO[].class);

        //assembling view
        ModelAndView mv = new ModelAndView("optionSettings");
        mv.addObject(optionDTO);
        return mv;*/

        OptionDTO optionDTO = modelMapper.map(optionService.getOption(OptionKey.SPEED_FAC), OptionDTO.class);
        return new ModelAndView("optionSettings", "optionDTO", optionDTO);
    }
}
