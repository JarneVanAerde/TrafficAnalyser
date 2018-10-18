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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/user")
public class UserWebController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserWebController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ModelAndView showLogin(UserDTO userDTO) {
        return new ModelAndView("login", "userDTO", userDTO);
    }

    @PostMapping("/create")
    public RedirectView makeUser(@ModelAttribute UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userService.makeNewUserIfNeeded(user);
        return new RedirectView("menu");
    }

    @GetMapping("/menu")
    public ModelAndView showMenu() {
        return new ModelAndView("menu");
    }

}
