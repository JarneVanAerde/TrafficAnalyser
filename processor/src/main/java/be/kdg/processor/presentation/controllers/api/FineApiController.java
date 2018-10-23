package be.kdg.processor.presentation.controllers.api;

import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.presentation.dto.FineDTO;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.modelservices.FineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This api controller is used for
 * CRUD REST class thar are related to fines.
 */
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

    /**
     * @return all the fines.
     */
    @GetMapping("/fines")
    public ResponseEntity<FineDTO[]> loadFines() {
        List<Fine> fines = fineService.getFines();
        if (fines.size() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(modelMapper.map(fines, FineDTO[].class), HttpStatus.OK);
    }

    /**
     * @param before before date.
     * @param after after date.
     * @return all the fines between two dates.
     */
    @GetMapping("/fines/}{before}/{after}")
    public ResponseEntity<FineDTO[]> getFinesBetweenDates(@PathVariable LocalDateTime before, @PathVariable LocalDateTime after) {
        List<Fine> fines = fineService.getFinesBetweenDates(before, after);
        if (fines.size() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(modelMapper.map(fines, FineDTO[].class), HttpStatus.OK);
    }

    /**
     * @param id the id of the fine.
     * @return one fine for construction a detailed view for that fine.
     * @throws ServiceException is handled by ServiceExceptionHandler.
     */
    @GetMapping("/fines/{id}")
    public ResponseEntity<FineDTO> loadFineDetails(@PathVariable int id) throws ServiceException {
        Fine fine = fineService.getFine(id);
        return new ResponseEntity<>(modelMapper.map(fine, FineDTO.class), HttpStatus.OK);
    }

    /**
     * @param id the id of the fine that needs to be approved.
     * @return approves that a fine was correctly created.
     * @throws ServiceException is handled by ServiceExceptionHandler.
     */
    @PutMapping("/fines/approve/{id}")
    public ResponseEntity<FineDTO> approveFine(@PathVariable int id) throws ServiceException {
        Fine approvedFine = fineService.approveFine(id);
        return new ResponseEntity<>(modelMapper.map(approvedFine, FineDTO.class), HttpStatus.OK);
    }

    /**
     * @param id the id of the fine amount that needs to be updated
     * @param amount the new amount
     * @param message message that explains the amount
     * @return updates the amount of a specific fine.
     * @throws ServiceException is handled by ServiceExceptionHandler.
     */
    @PutMapping("/fines/updateAmount/{id}/{amount}/{message}")
    public ResponseEntity<FineDTO> updateAmount(@PathVariable int id,
                                                @PathVariable double amount,
                                                @PathVariable String message) throws ServiceException {
        Fine updatedFine = fineService.changeAmount(id, amount, message);
        return new ResponseEntity<>(modelMapper.map(updatedFine, FineDTO.class), HttpStatus.OK);
    }
}
