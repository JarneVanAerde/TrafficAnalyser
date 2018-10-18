package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.presentation.dto.UserDTO;
import be.kdg.processor.services.impl.modelservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserWebController {
    private final UserService userService;

    @Autowired
    public UserWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView showGreetingsForm(UserDTO userDTO) {
        return new ModelAndView("login", "userDTO", userDTO);
    }
}
