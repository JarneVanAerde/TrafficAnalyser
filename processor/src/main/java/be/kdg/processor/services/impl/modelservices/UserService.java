package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.users.User;
import be.kdg.processor.persistence.UserRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers(boolean deleted) {
        return userRepository.findAllByDeleted(deleted);
    }

    public User getUser(int id) throws ServiceException {
        return userRepository.findById(id)
                //.filter(user -> !user.isDeleted())
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": user with id " + id + " wasn't found in the database"));
    }

    public User deleteUser(int id) throws ServiceException {
        User userToDelete = getUser(id);
        userToDelete.setDeleted(true);
        return saveUser(userToDelete);
    }
}
