package com.finance.dash_api.controller;

import com.finance.dash_api.DTO.LoginDTO;
import com.finance.dash_api.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Login Api", description = "Login and get Access Token. (please get default admin credentials for github readme)")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController( AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginDTO cridentials) throws Exception {

        String token = authService.getUser(cridentials);
        return Map.of("token", token);
    }


}
