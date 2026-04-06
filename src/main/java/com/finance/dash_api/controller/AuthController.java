package com.finance.dash_api.controller;

import com.finance.dash_api.security.JWTUtility;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JWTUtility jwtUtil;

    public AuthController(JWTUtility jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) throws Exception {

        String username = request.get("username");
        String password = request.get("password");

        // 🔥 TEMP: Hardcoded users (fast approach)
        if ("admin".equals(username) && "admin123".equals(password)) {
            String token = jwtUtil.generateToken(username, "ADMIN");
            return Map.of("token", token);
        }

        if ("analyst".equals(username) && "analyst123".equals(password)) {
            String token = jwtUtil.generateToken(username, "ANALYST");
            return Map.of("token", token);
        }

        if ("viewer".equals(username) && "viewer123".equals(password)) {
            String token = jwtUtil.generateToken(username, "VIEWER");
            return Map.of("token", token);
        }

        throw new Exception("Invalid credentials");
    }
}