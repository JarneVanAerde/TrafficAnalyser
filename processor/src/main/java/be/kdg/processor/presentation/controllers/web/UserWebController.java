package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.presentation.dto.UserDTO;
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
     * @param userDTO used by the login form
     * @return a login view with a userDTO
     */
    @GetMapping("/login")
    public ModelAndView showLogin(UserDTO userDTO) {
        return new ModelAndView("login", "userDTO", userDTO);
    }

    /**
     * @return a menu view.
     */
    @GetMapping("/menu")
    public ModelAndView showMenu() {
        return new ModelAndView("menu");
    }

}
