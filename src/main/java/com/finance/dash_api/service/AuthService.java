package com.finance.dash_api.service;

import com.finance.dash_api.DTO.LoginDTO;
import com.finance.dash_api.POJO.CustomException;
import com.finance.dash_api.entity.User;
import com.finance.dash_api.repository.UserRepository;
import com.finance.dash_api.security.JWTUtility;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final JWTUtility jwtUtil;

    public AuthService(UserRepository userRepo, JWTUtility jwtUtil){
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;

    }

    public String  getUser(LoginDTO cridentials){

        String email = cridentials.getEmail();
        String password = cridentials.getPassword();
        String id ;
        String role;
        try{
            User user = userRepo.findByEmail(email);
            if(user == null){
                throw new NoSuchFieldException("User with email Not Found");
            }
            if(!password.equals(user.getPassword())){
                throw new CustomException("Password Incorrect, Please Enter Correct Password",HttpStatus.FORBIDDEN);
            }
            id = user.getId().toString();
            role = user.getRole().name();
        } catch (NoSuchFieldException e) {
            throw new CustomException(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return jwtUtil.generateToken(id,role);
    }
}
