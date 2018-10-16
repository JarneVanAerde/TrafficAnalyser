package be.kdg.processor.presentation.controllers.api;

import be.kdg.processor.models.options.Option;
import be.kdg.processor.presentation.dto.OptionDTO;
import be.kdg.processor.services.impl.modelservices.OptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OptionApiController {
    private final OptionService optionService;
    private final ModelMapper modelMapper;

    @Autowired
    public OptionApiController(OptionService optionService, ModelMapper modelMapper) {
        this.optionService = optionService;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/updateOption")
    public ResponseEntity<OptionDTO> updateOption(@RequestBody OptionDTO optionDTO) {
        Option optionToUpdate = optionService.saveOption(modelMapper.map(optionDTO, Option.class));
        return new ResponseEntity<>(modelMapper.map(optionToUpdate, OptionDTO.class), HttpStatus.OK);
    }
}
