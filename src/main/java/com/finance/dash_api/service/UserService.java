package com.finance.dash_api.service;

import com.finance.dash_api.POJO.ExceptionPOJO;
import com.finance.dash_api.entity.User;
import com.finance.dash_api.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(User user) {
        //null
        //validation
        //check for existing
        if (user.getName().equals("")) throw new ExceptionPOJO("Failed", "user can't be null", HttpStatus.BAD_REQUEST);

        User newUser = userRepository.save(user);

        if (user.equals(newUser)) {
            return true;
        }
        return false;
    }

    public List<User> getAllUsers(int page, int size) {
        return userRepository.findAll();
    }

    public boolean delUser(UUID id) {

        User user;
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public boolean updateUser(UUID id, User newUserDetail) {
        log.info(String.valueOf(id));
        //validation id = newUserDetail.id
        //validation notnull
        User existingUserDetail;
        if (userRepository.findById(id).isPresent()) {
            existingUserDetail = userRepository.findById(id).get();
            if (newUserDetail.getPassword() != null && !(newUserDetail.getPassword().equals(existingUserDetail.getPassword()))) {
                existingUserDetail.setPassword(newUserDetail.getPassword());
            }
            if (newUserDetail.getRole() != null && !(newUserDetail.getRole().equals(existingUserDetail.getRole()))) {
                log.info(";rioo;");
                existingUserDetail.setRole(newUserDetail.getRole());
            }
            if (newUserDetail.isActive() != existingUserDetail.isActive()) {
                existingUserDetail.setActive(newUserDetail.isActive());
            }

            User user = userRepository.save(existingUserDetail);

            return true;
        }

        return false;

    }

}