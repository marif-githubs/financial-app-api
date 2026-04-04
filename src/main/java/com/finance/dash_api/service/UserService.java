package com.finance.dash_api.service;

import com.finance.dash_api.POJO.CustomException;
import com.finance.dash_api.entity.User;
import com.finance.dash_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(User user) {

        User newUser;

        try {
            //check if user already exist
            newUser = userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return newUser.getId();
    }

    //need to fix pagination
    public List<User> getAllUsers(int page, int size) {
        return userRepository.findAll();
    }

    public UUID deleteUser(UUID id) {

        if (id.toString().isBlank())
            throw new CustomException("Enter Valid User Id", HttpStatus.BAD_REQUEST);

        userRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException("User not found", HttpStatus.NOT_FOUND));
        try {

            userRepository.deleteById(id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return id;

    }

    public User updateUser(UUID id, User newUserDetail) {

        User user;
        User existingUserDetail = userRepository.findById(id).
                orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        try {

            if (newUserDetail.getPassword() != null && !(newUserDetail.getPassword().equals(existingUserDetail.getPassword()))) {
                existingUserDetail.setPassword(newUserDetail.getPassword());
            }
            if (newUserDetail.getRole() != null && !(newUserDetail.getRole().equals(existingUserDetail.getRole()))) {
                existingUserDetail.setRole(newUserDetail.getRole());
            }
            if (newUserDetail.isActive() != existingUserDetail.isActive()) {
                existingUserDetail.setActive(newUserDetail.isActive());
            }

            user = userRepository.save(existingUserDetail);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public User activateUser(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        user.setActive(true);

        try {

            userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public User deactivateUser(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        user.setActive(false);

        try {

            userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user;
    }

}