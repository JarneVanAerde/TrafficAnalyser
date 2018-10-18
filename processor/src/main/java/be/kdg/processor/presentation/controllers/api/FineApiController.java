package be.kdg.processor.presentation.controllers.api;

import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.presentation.dto.FineDTO;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FineApiController {
    private final FineService fineService;
    private final ModelMapper modelMapper;

    @Autowired
    public FineApiController(FineService fineService, ModelMapper modelMapper) {
        this.fineService = fineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/fines")
    public ResponseEntity<FineDTO[]> loadFines() throws ServiceException {
        List<Fine> fines = fineService.getFines();
        if (fines.size() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(modelMapper.map(fines, FineDTO[].class), HttpStatus.OK);
    }

    @GetMapping("/fines/{id}")
    public ResponseEntity<FineDTO> loadFineDetails(@PathVariable int id) throws ServiceException {
        Fine fine = fineService.getFine(id);
        return new ResponseEntity<>(modelMapper.map(fine, FineDTO.class), HttpStatus.OK);
    }

    @PutMapping("/fines/approve/{id}")
    public ResponseEntity<FineDTO> approveFine(@PathVariable int id) throws ServiceException {
        Fine approvedFine = fineService.approveFine(id);
        return new ResponseEntity<>(modelMapper.map(approvedFine, FineDTO.class), HttpStatus.OK);
    }

    @PutMapping("/fines/updateAmount/{id}")
    public ResponseEntity<FineDTO> updateAmount(@PathVariable int id, @RequestBody @Valid FineDTO fineDTO) throws ServiceException {
        Fine updatedFine = fineService.changeAmount(id, fineDTO.getAmount(), fineDTO.getMotivation());
        return new ResponseEntity<>(modelMapper.map(updatedFine, FineDTO.class), HttpStatus.OK);
    }
}