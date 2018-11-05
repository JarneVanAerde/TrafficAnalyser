package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.users.Role;
import be.kdg.processor.models.users.User;
import be.kdg.processor.services.exceptions.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void saveUserSucces() throws ServiceException {
        User user = userService.saveUser(new User("jarne", "jarne", Collections.singletonList(new Role("ADMIN"))));
        assertEquals(userService.getUser(user.getId()).getUsername(), user.getUsername());
    }

    @Test(expected = ServiceException.class)
    public void saveUserFail() throws ServiceException {
        userService.saveUser(new User("jos", "jos", Collections.singletonList(new Role("ADMIN"))));
        userService.saveUser(new User("jos", "jos", Collections.singletonList(new Role("ADMIN"))));
        fail("You should not be able to create 2 users with identical username");
    }

    @Test(expected = ServiceException.class)
    public void deleteUser() throws ServiceException {
        User user = userService.saveUser(new User("bart", "bart", Collections.singletonList(new Role("ADMIN"))));
        user = userService.deleteUser(user.getId());
        userService.getUser(user.getId());
        fail("Deleted users should not be returned");
    }
}