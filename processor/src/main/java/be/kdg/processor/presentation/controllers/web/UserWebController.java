package be.kdg.processor.presentation.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * web controller used for the user related views
 */
@Controller
@RequestMapping("/user")
public class UserWebController {
    /**
     * @return a login view.
     */
    @GetMapping("/login")
    public ModelAndView showLogin() {
        return new ModelAndView("login");
    }

    /**
     * @return a menu view.
     */
    @GetMapping("/menu")
    public ModelAndView showMenu() {
        return new ModelAndView("menu");
    }
}
