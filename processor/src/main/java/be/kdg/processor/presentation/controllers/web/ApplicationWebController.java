package be.kdg.processor.presentation.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class ApplicationWebController {
    private boolean isShutdown = false;

    @GetMapping("/toggle")
    public ModelAndView toggleApp() {
        if (isShutdown) return new ModelAndView("redirect:/option/pause");
        else return new ModelAndView("redirect:/option/run");
    }

    @GetMapping("/pause")
    public ModelAndView showPaused() {
        isShutdown = true;
        return new ModelAndView("appPaused");
    }

    @GetMapping("/run")
    public ModelAndView showShutDown() {
        isShutdown = false;
        return new ModelAndView("appRunning");
    }
}
