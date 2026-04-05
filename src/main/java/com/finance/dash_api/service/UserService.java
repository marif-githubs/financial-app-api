package com.finance.dash_api.service;

import com.finance.dash_api.DTO.UserDTO;
import com.finance.dash_api.Helper.MapToDTO;
import com.finance.dash_api.POJO.CustomException;
import com.finance.dash_api.entity.User;
import com.finance.dash_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MapToDTO mapToDTO;

    public UserService(UserRepository userRepository, MapToDTO mapToDTO) {
        this.userRepository = userRepository;
        this.mapToDTO = mapToDTO;
    }

    public UUID createUser(UserDTO userDTO) {

        //check if user already exist
//            try {
//                userRepository.save(user);
//            } catch (DataIntegrityViolationException e) {
//                throw new CustomException("Email already exists", HttpStatus.CONFLICT);
//            }
        User user = mapToDTO.toUser(userDTO);
        User newUser = userRepository.save(user);


        return newUser.getId();
    }

    public Page<UserDTO> getAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable)
                .map(mapToDTO::toDTO);
    }

    public UUID deleteUser(UUID id) {

        if (id.toString().isBlank())
            throw new CustomException("Enter Valid User Id", HttpStatus.BAD_REQUEST);

        userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        userRepository.deleteById(id);

        return id;

    }

    public UserDTO updateUser(UUID id, UserDTO newUserDetail) {

        User user;
        User existingUserDetail = userRepository.findById(id).
                orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        if (newUserDetail.getName() != null && !(newUserDetail.getName().equals(existingUserDetail.getName()))) {
            existingUserDetail.setName(newUserDetail.getName());
        }
        if (newUserDetail.getRole() != null && !(newUserDetail.getEmail().equals(existingUserDetail.getEmail()))) {
            existingUserDetail.setEmail(newUserDetail.getEmail());
        }
        if (newUserDetail.isActive() != existingUserDetail.isActive()) {
            existingUserDetail.setActive(newUserDetail.isActive());
        }

        user = userRepository.save(existingUserDetail);


        return mapToDTO.toDTO(user);
    }

    public UserDTO activateUser(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        user.setActive(true);

        userRepository.save(user);

        return mapToDTO.toDTO(userRepository.save(user));
    }

    public UserDTO deactivateUser(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        user.setActive(false);

        return mapToDTO.toDTO(userRepository.save(user));
    }

}