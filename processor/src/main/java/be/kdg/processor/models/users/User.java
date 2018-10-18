package be.kdg.processor.models.users;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

/**
 * Simple POJO used to store information about a user
 * All users are admins.
 */
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String name;
    private String password;
    @Setter
    private boolean deleted;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
