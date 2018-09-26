package com.example.easynotes.service;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> getById(long userId){
        if(userRepository.findById(userId) == null)
            throw new ResourceNotFoundException("Userid "+userId+" not found");
        Optional<User> user =  userRepository.findById(userId);
        return user;
    }

    @Transactional
    public User createUser(User user){
        User save = this.userRepository.save(user);
        return save;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void delete(User user){
        userRepository.delete(user);
    }
}
