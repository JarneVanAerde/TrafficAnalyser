package be.kdg.processor.presentation.controllers.api;

import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.presentation.dto.FineDTO;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.PersistenceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FineRestController {
    private final FineService fineService;
    private final ModelMapper modelMapper;

    @Autowired
    public FineRestController(FineService fineService, ModelMapper modelMapper) {
        this.fineService = fineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/fines")
    public ResponseEntity<FineDTO[]> loadFines() {
        List<Fine> fines = fineService.getFines();
        if (fines.size() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(modelMapper.map(fines, FineDTO[].class), HttpStatus.OK);
    }

    @GetMapping("/fines/{id}")
    public ResponseEntity<FineDTO> loadFineDetails(@PathVariable int id) throws PersistenceException {
        Fine fine = fineService.getFine(id);
        return new ResponseEntity<>(modelMapper.map(fine, FineDTO.class), HttpStatus.OK);
    }
}
