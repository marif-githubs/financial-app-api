package com.finance.dash_api.controller;

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
    public ResponseEntity<ResponseEntityPOJO<Void>> createUser(@Valid @RequestBody User user) {

        boolean created = userService.createUser(user);

        if (created) {
            //send user id on success.
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseEntityPOJO<>("Success", "Resource Created", null));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ResponseEntityPOJO<>("Failure", "Provide valid User detail", null));
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
    public ResponseEntity<ResponseEntityPOJO<Object>> deleteUser(@PathVariable UUID id) {

        boolean deleted = userService.delUser(id);

        if(deleted){
            return ResponseEntity.ok(new ResponseEntityPOJO<>("Success", "User Deleted", null));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseEntityPOJO<>("Failed", "User Not Found", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntityPOJO<Object>> updateUser(@RequestBody User user, @PathVariable UUID id) {

        boolean updated = userService.updateUser(id, user);

        if (updated) {

            return ResponseEntity.ok(new ResponseEntityPOJO<>("Success", "User Detail Updated", null));

        }
        return ResponseEntity.ok(new ResponseEntityPOJO<>("Failed", "Provide valid User detail", null));
    }
}