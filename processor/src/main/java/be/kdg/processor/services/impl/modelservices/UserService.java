package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.users.Role;
import be.kdg.processor.models.users.User;
import be.kdg.processor.persistence.UserRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * This service is used for user CRUD.
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        addSuperAdmin();
    }

    /**
     * Adds the super admin for the application
     */
    private void addSuperAdmin() {
       Role adminRole = new Role("ADMIN");

       User user = new User();
       user.setUsername("sa");
       user.setPassword("sa");
       user.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
       saveUser(user);
    }

    /**
     * @param user user to save
     * @return a user with an id
     */
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //Role userRole = roleRepository.findByRole("ADMIN");
        //user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }

    /**
     * @param deleted decides if the methods returns active or deleted users
     * @return all the requested users
     */
    public List<User> getUsers(boolean deleted) {
        return userRepository.findAllByDeleted(deleted);
    }

    /**
     * @param id user id
     * @return the requested user
     * @throws ServiceException wrapper-exception
     */
    public User getUser(int id) throws ServiceException {
        return userRepository.findById(id)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": user with id " + id + " wasn't found in the database"));
    }

    /**
     * @param id user id that needs to be deleted
     * @return the deteled user
     * @throws ServiceException wrapper-exception
     */
    public User deleteUser(int id) throws ServiceException {
        User userToDelete = getUser(id);
        userToDelete.setDeleted(true);
        return saveUser(userToDelete);
    }
}
