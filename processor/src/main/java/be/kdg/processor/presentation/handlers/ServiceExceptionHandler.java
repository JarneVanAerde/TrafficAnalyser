package be.kdg.processor.presentation.handlers;

import be.kdg.processor.services.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * handles all the service-based exception that are thrown throughout the application
 * and that aren't catched.
 */
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(value = ServiceException.class)
    public ModelAndView handleServiceExcpetion(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ModelAndView("error");
    }
}
