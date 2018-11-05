package be.kdg.processor.models.users;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

/**
 * Simple POJO used to store information about a user
 * All users are admins.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private boolean deleted;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(@NotEmpty String username, @NotEmpty String password, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.deleted = false;
    }
}
