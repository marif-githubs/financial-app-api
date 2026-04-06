package com.finance.dash_api.controller;

import com.finance.dash_api.DTO.UserDTO;
import com.finance.dash_api.POJO.UserId;
import com.finance.dash_api.service.UserService;
import com.finance.dash_api.POJO.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ApiResponse<UserId> createUser(@Valid @RequestBody UserDTO user) {

        UUID createdUserId = userService.createUser(user);

        return new ApiResponse<>("Success", user.getName() + " User Created", new UserId(createdUserId));

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST')")
    public ApiResponse<Page<UserDTO>> getUsers(@RequestParam(required = false, defaultValue = "0") int pageNum, @RequestParam(required = false, defaultValue =
            "10") int size) {

        Page<UserDTO> page = userService.getAllUsers(pageNum, size);

        return new ApiResponse<>("Success", "Total Users Found:"+page.getTotalElements()+" page:" + pageNum + " size:" + size, page);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<UserId> deleteUser(@PathVariable UUID id) {

        UUID deleteUserId = userService.deleteUser(id);

        return new ApiResponse<>("Success", "User Deleted", new UserId(deleteUserId));

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<UserDTO> updateUser(@RequestBody UserDTO user, @PathVariable UUID id) {

        UserDTO updatedUserDetail = userService.updateUser(id, user);

        return new ApiResponse<>("Success", "User Detail Updated", updatedUserDetail);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/activate")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<UserId> activate(@PathVariable UUID id) {

        UserDTO user = userService.activateUser(id);

        return new ApiResponse<>("Success", "User:" + user.getName() + " Profile Activated", new UserId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<UserId> deactivate(@PathVariable UUID id) {

        UserDTO user = userService.deactivateUser(id);

        return new ApiResponse<>("Success", user.getName() + " Profile Deactivated", new UserId(id));
    }
}

