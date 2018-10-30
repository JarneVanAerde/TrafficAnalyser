package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.models.users.User;
import be.kdg.processor.services.impl.modelservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
     * @return a login view.
     */
    @GetMapping("/login")
    public ModelAndView showLoginAndRegister() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.getUser(user.getUsername());

        if (userExists != null) {
            bindingResult.rejectValue("username", "error.user","There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("login");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/user/login");
        }

        return modelAndView;
    }

    /**
     * @return a menu view.
     */
    @GetMapping("/menu")
    public ModelAndView showMenu() {
        return new ModelAndView("menu");
    }
}
