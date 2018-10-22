package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.models.users.User;
import be.kdg.processor.presentation.dto.UserDTO;
import be.kdg.processor.services.impl.modelservices.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * web controller used for the user related views
 */
@Controller
@RequestMapping("/user")
public class UserWebController {
    private final UserService userService;

    @Autowired
    public UserWebController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return a login view with an empty userDTO
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

    /**
     * @param userDTO user to authenticate.
     * @return the corresponding view based on the users authentication.
     */
    @PostMapping("/verify")
    public ModelAndView verifyLogin(@ModelAttribute @Valid UserDTO userDTO) {
        if (userService.authenticateUser(userDTO.getName(), userDTO.getPassword())) return new ModelAndView("redirect:/user/menu");
        else return new ModelAndView("redirect:/user/login");
    }
}
