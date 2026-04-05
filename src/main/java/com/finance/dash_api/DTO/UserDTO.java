package com.finance.dash_api.DTO;

import com.finance.dash_api.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    @NotBlank
    @Size(min= 2, max = 30)
    private String name;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8, max = 20)
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be strong " +
                    "At least 8 characters " +
                    "At least 1 lowercase " +
                    "At least 1 uppercase " +
                    "At least 1 digit " +
                    "At least 1 special character")
    private String password;

    private UserRole role;

    private boolean active;

    private LocalDateTime creationDate;

}