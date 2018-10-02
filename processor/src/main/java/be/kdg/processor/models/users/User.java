package be.kdg.processor.models.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class User {
    private int userId;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
