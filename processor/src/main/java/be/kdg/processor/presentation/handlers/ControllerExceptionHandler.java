package be.kdg.processor.presentation.handlers;

import be.kdg.processor.services.exceptions.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles all controller-based exceptions that are thrown from the controller.
 */
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = ControllerException.class)
    public ModelAndView handleControllerException(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ModelAndView("error");
    }
}
