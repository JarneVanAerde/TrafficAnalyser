package be.kdg.processor.presentation.controllers.api;

import be.kdg.processor.models.users.User;
import be.kdg.processor.presentation.dto.UserDTO;
import be.kdg.processor.services.exceptions.ServiceException;
import be.kdg.processor.services.impl.modelservices.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserApiController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/active")
    public ResponseEntity<UserDTO[]> loadUsers() {
        List<User> users = userService.getUsers(false);
        return new ResponseEntity<>(modelMapper.map(users, UserDTO[].class), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> loadUser(@PathVariable int id) throws ServiceException {
        User user = userService.getUser(id);
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @PostMapping("/users/save")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User createdUser = userService.saveUser(modelMapper.map(userDTO, User.class));
        return new ResponseEntity<>(modelMapper.map(createdUser, UserDTO.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable int id) throws ServiceException {
        User userToDelete = userService.deleteUser(id);
        return new ResponseEntity<>(modelMapper.map(userToDelete, UserDTO.class), HttpStatus.CREATED);
    }
}
