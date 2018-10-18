package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.models.users.User;
import be.kdg.processor.presentation.dto.UserDTO;
import be.kdg.processor.services.impl.modelservices.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView showGreetingsForm(UserDTO userDTO) {
        return new ModelAndView("login", "userDTO", userDTO);
    }

    @GetMapping("/")
    public ModelAndView showGreetingsForm() {
        return new ModelAndView("index");
    }

    @GetMapping("/menu")
    public ModelAndView showMenu(@ModelAttribute UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userService.makeNewUserIfNeeded(user);
        return new ModelAndView("menu");
    }
}
