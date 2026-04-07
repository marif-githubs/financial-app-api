package com.finance.dash_api.DTO;

import com.finance.dash_api.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class resUserDto {

    private String  id;

    private String name;

    private String email;

    private String  role;

    private boolean active;

    private LocalDateTime creationDate;
}
