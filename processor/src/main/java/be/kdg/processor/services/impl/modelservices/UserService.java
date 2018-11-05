package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.users.Role;
import be.kdg.processor.models.users.User;
import be.kdg.processor.persistence.RoleRepository;
import be.kdg.processor.persistence.UserRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service is used for user CRUD.
 */
@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Post construct is needed to be sure that the role name is inserted
     * Adds the super admin for the application
     */
    @PostConstruct
    private void addSuperAdmin() {
      User user = new User("sa", bCryptPasswordEncoder.encode("sa"),
              Collections.singletonList(new Role("ADMIN")));

      userRepository.save(user);
    }

    /**
     * @param user user to save
     * @return a user with an id
     * @throws ServiceException is thrown is user already exists.
     */
    public User saveUser(User user) throws ServiceException {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new ServiceException("User already exists.");

        userRepository.save(user);
        return user;
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

    /**
     * @param roles roles to be mapped.
     * @return mapped roles
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    /**
     * @param username name of the requested login user
     * @return The user details of the requested user.
     * @throws UsernameNotFoundException is thrown if the username was not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username);
       if (user == null) throw new UsernameNotFoundException("Invalid username of password.");
       else {
           return new org.springframework.security.core.userdetails.User(user.getUsername(),
                   user.getPassword(), mapRolesToAuthorities(user.getRoles()));
       }
    }
}
