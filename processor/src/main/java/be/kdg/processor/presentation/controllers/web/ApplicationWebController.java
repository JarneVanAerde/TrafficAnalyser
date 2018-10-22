package be.kdg.processor.presentation.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class ApplicationWebController {
    @GetMapping("/pause")
    public ModelAndView showPaused() {
        return new ModelAndView("appPaused");
    }

    @GetMapping("/run")
    public ModelAndView showShutDown() {
        return new ModelAndView("appRunnin");
    }
}
