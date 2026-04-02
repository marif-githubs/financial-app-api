package com.finance.dash_api.service;

import com.finance.dash_api.entity.User;
import com.finance.dash_api.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
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

    public User createUser(User user) {
        log.info(user.getEmail() + " " + user.getName() + " " + user.getRole() + " " + user.getPassword());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User delUser(UUID id) {
        log.info(String.valueOf(id));

        User user = userRepository.findById(id).get();
        log.info(user.getEmail() + " " + user.getName() + " " + user.getRole() + " " + user.getPassword());

        if (user != null) {
            userRepository.deleteById(id);
        }

        return user;
    }

    public User updateUser(UUID id, User newUserDetail) {
        log.info(String.valueOf(id));
        //validation id = newUserDetail.id
        //validation notnull
        User existingUserDetail = userRepository.findById(id).get();
        if (existingUserDetail != null) {

            if (newUserDetail.getPassword() != null && newUserDetail.getPassword() != existingUserDetail.getPassword()) {
                existingUserDetail.setPassword(newUserDetail.getPassword());
            }
            if (newUserDetail.getRole() != null && newUserDetail.getRole().equals(newUserDetail.getRole())) {
                existingUserDetail.setRole(newUserDetail.getRole());
            }
            if (newUserDetail.isActive() != existingUserDetail.isActive()) {
                existingUserDetail.setActive(newUserDetail.isActive());
            }

            return userRepository.save(existingUserDetail);
        }

        return userRepository.save(existingUserDetail);

    }


}