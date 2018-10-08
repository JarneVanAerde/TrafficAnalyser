package be.kdg.processor.models.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * Simple POJO used to store information about a user
 * All users are admins.
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class User {
    private int userId;
    private String name;

    @Override
    public String toString() {
        return userId + " " + name;
    }
}
