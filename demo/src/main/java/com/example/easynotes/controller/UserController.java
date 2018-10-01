package com.example.easynotes.controller;

import com.example.easynotes.dto.ApplicationUserDto;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.model.ApplicationUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.easynotes.exception.ResourceNotFoundException;

import com.example.easynotes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private DtoManager dtoManager = new DtoManager();

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public Long createUser(@Valid @RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return user.getId();
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, @Valid @RequestBody ApplicationUser userRequest) throws ParseException {
        ApplicationUserDto savedUser = userService.getById(userId);
        if(savedUser != null){
            ApplicationUser user = dtoManager.convertToEntity(savedUser);
            user.setUsername(userRequest.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            userService.createUser(user);
        }
        else new ResourceNotFoundException("UserId "+userId+" not found");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) throws ParseException {
        ApplicationUserDto user = userService.getById(userId);
        if(user == null)
            throw new ResourceNotFoundException("UserId "+userId+ " not found");
        userService.delete(dtoManager.convertToEntity(user));
        return ResponseEntity.ok().build();
    }
}
