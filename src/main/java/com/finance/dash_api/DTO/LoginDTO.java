package com.finance.dash_api.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginDTO {

    @NotBlank
    @Email(message = "Enter Valid Email")
    private String email;
    @Size(min = 8, max = 40, message = "Password length must be between 8 to 40")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Enter valid password")
    private String password;
}
