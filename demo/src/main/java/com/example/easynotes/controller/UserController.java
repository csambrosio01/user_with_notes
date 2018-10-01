package com.example.easynotes.controller;

import com.example.easynotes.dto.ApplicationUserDto;
import com.example.easynotes.model.ApplicationUser;

import com.example.easynotes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ApplicationUserDto createUser(@Valid @RequestBody ApplicationUser user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public ApplicationUserDto updateUser(@PathVariable Long userId, @Valid @RequestBody ApplicationUser userRequest) throws ParseException {
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) throws ParseException {
        return userService.deleteUser(userId);
    }
}
