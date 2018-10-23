package be.kdg.processor.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * UserDTO is used for transferring user information.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private int userId;
    @NotNull
    private String name;
    @NotNull
    private String password;
    private boolean deleted;
}
