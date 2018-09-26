package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.User;
import com.example.easynotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user){
        User savedUser = userService.createUser(user);
        return savedUser;
    }

    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable Long userId, @Valid @RequestBody User userRequest) {
        return userService.getById(userId).map(user -> {user.setUsername(userRequest.getUsername()); return userService.createUser(user); }).orElseThrow(() -> new ResourceNotFoundException("UserId "+userId+" not found"));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        Optional<User> user = userService.getById(userId);
        if(user == null)
            throw new ResourceNotFoundException("UserId "+userId+ " not found");
        userService.delete(user.get());
        return ResponseEntity.ok().build();
    }
}
