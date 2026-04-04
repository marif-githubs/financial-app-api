package com.finance.dash_api.controller;

import com.finance.dash_api.POJO.UserIdPOJO;
import com.finance.dash_api.entity.User;
import com.finance.dash_api.service.UserService;
import com.finance.dash_api.POJO.ResponseEntityPOJO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntityPOJO<UserIdPOJO> createUser(@Valid @RequestBody User user) {

        UUID createdUserId = userService.createUser(user);

        return new ResponseEntityPOJO<>("Success", "Resource Created", new UserIdPOJO(createdUserId));

    }

    @GetMapping
    public ResponseEntity<ResponseEntityPOJO<List<User>>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size,
                                                                      Pageable pageable) {
        //start form here pagenation.
        List<User> userslist = userService.getAllUsers(page, size);

        if (!userslist.isEmpty()) {
            return ResponseEntity.ok(new ResponseEntityPOJO<>("Success", "Found Users " + userslist.toArray().length, userslist));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseEntityPOJO<>("Failure", "No User Found", null));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntityPOJO<UserIdPOJO> deleteUser(@PathVariable UUID id) {

        UUID deleteUserId = userService.deleteUser(id);

        return new ResponseEntityPOJO<>("Success", "User Deleted", new UserIdPOJO(deleteUserId));

    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntityPOJO<User> updateUser(@RequestBody User user, @PathVariable UUID id) {

        User updatedUserDetail = userService.updateUser(id, user);

        return  new ResponseEntityPOJO<>("Success", "User Detail Updated", updatedUserDetail);

    }

    @PatchMapping("/{id}/activate")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntityPOJO<UserIdPOJO> activate(@PathVariable UUID id) {

        User user = userService.activateUser(id);

        return new ResponseEntityPOJO<>("Success", "User:"+user.getName()+" Profile Activated", new UserIdPOJO(id));
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntityPOJO<UserIdPOJO> deactivate(@PathVariable UUID id) {

        User user = userService.deactivateUser(id);

        return new ResponseEntityPOJO<>("Success", user.getName()+" Profile Deactivated", new UserIdPOJO(id));
    }
}

