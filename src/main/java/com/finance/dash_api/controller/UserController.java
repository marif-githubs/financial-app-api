package com.finance.dash_api.controller;

import com.finance.dash_api.entity.User;
import com.finance.dash_api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public User create(@RequestBody User user) {
        log.info(user.getEmail()+" "+user.getName()+" "+user.getRole()+" "+user.getPassword());
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public User delUser(@PathVariable UUID id){
        log.info(String.valueOf(id));
        return userService.delUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable UUID id){
        return userService.updateUser(id, user);
    }
}