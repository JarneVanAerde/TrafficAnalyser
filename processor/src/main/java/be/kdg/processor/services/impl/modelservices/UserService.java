package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.users.User;
import be.kdg.processor.persistence.UserRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This service is used for user CRUD.
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        addSuperAdmin();
    }

    /**
     * Adds the super admin for the application
     */
    public void addSuperAdmin() {
        saveUser(new User("sa", "sa"));
    }

    /**
     * @param user user to save
     * @return a user with an id
     */
    public User saveUser(User user) {
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
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": user with cameraId " + id + " wasn't found in the database"));
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


    /**
     * @param user user from login page
     * @return a new or existing user.
     */
    public User makeNewUserIfNeeded(User user) {
        if (!userRepository.existsById(user.getUserId())) return saveUser(user);
        else return user;
    }
}
