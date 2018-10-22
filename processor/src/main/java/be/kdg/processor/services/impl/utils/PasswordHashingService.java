package be.kdg.processor.services.impl.utils;

import be.kdg.processor.services.exceptions.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PasswordHashingService {
    /*public static String hashPassword(String password) throws ServiceException {
        return Arrays.stream(password.split(""))
                .map(chr -> (char) (chr + 6))
                .map(chr -> chr + chr)
                .findFirst().get();
    }

    public static String unHashPassword(String hashedPassword) {
        Arrays.stream(hashedPassword.split(""))
                .map(chr -> chr - 6)
                .map(chr -> chr + chr)
                .findFirst().get();
        return integer;
    }*/
}
