package com.example.easynotes.controller;

import com.example.easynotes.model.ApplicationUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.easynotes.exception.ResourceNotFoundException;

import com.example.easynotes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public Long createUser(@Valid @RequestBody ApplicationUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return user.getId();
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @Valid @RequestBody ApplicationUser userRequest) {
        Optional<ApplicationUser> savedUser = userService.getById(userId);
        if(savedUser.isPresent()){
            ApplicationUser user = savedUser.get();
            user.setUsername(userRequest.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            userService.createUser(user);
        }
        else new ResourceNotFoundException("UserId "+userId+" not found");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        Optional<ApplicationUser> user = userService.getById(userId);
        if(user == null)
            throw new ResourceNotFoundException("UserId "+userId+ " not found");
        userService.delete(user.get());
        return ResponseEntity.ok().build();
    }
}
