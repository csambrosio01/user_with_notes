package com.example.easynotes.service;

import com.example.easynotes.dto.ApplicationUserDto;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
public class UserService {
    private final UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private DtoManager dtoManager;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, DtoManager dtoManager){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dtoManager = dtoManager;
    }

    public ApplicationUserDto getById(Long userId){
        if(!userRepository.findById(userId).isPresent())
            throw new ResourceNotFoundException("UserId "+userId+" not found");
        ApplicationUser user =  userRepository.findById(userId).get();
        return dtoManager.convertToDto(user);
    }

    @Transactional
    public ApplicationUserDto createUser(ApplicationUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return dtoManager.convertToDto(user);
    }

    public ApplicationUser findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public ApplicationUserDto updateUser(Long userId, ApplicationUser userRequest) throws ParseException {
        ApplicationUserDto savedUser = getById(userId);
        if(savedUser != null){
            ApplicationUser user = dtoManager.convertToEntity(savedUser);
            user.setUsername(userRequest.getUsername());
            savedUser = dtoManager.convertToDto(user);
            return savedUser;
        }
        throw new ResourceNotFoundException("UserId "+userId+" not found");
    }

    public ResponseEntity<?> deleteUser(Long userId) throws ParseException {
        ApplicationUserDto user = getById(userId);
        if(user == null)
            throw new ResourceNotFoundException("UserId "+userId+ " not found");
        userRepository.delete(dtoManager.convertToEntity(user));
        return ResponseEntity.ok().build();
    }
}
