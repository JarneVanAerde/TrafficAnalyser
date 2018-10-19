package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.presentation.dto.FineDTO;
import be.kdg.processor.presentation.dto.FineDTOS;
import be.kdg.processor.presentation.dto.OptionDTOS;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/fine")
public class FineWebController {
    private final FineService fineService;
    private final ModelMapper modelMapper;

    @Autowired
    public FineWebController(FineService fineService, ModelMapper modelMapper) {
        this.fineService = fineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/settings")
    public ModelAndView showFines() throws ServiceException {
        List<Fine> fines = fineService.getFines().stream()
                .sorted(Comparator.comparing(Fine::getFineType))
                .collect(Collectors.toList());

        FineDTO[] fineDTOSArray = modelMapper.map(fines, FineDTO[].class);
        FineDTOS fineDTOS = new FineDTOS();
        Arrays.stream(fineDTOSArray).forEach(fineDTOS::addFineDTO);

        return new ModelAndView("fineSettings", "FineDTOS", fineDTOS);
    }
}
