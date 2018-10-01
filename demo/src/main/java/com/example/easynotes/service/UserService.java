package com.example.easynotes.service;

import com.example.easynotes.dto.ApplicationUserDto;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final NoteService noteService;

    private DtoManager dtoManager = new DtoManager();

    @Autowired
    public UserService(UserRepository userRepository, NoteService noteService){
        this.noteService = noteService;
        this.userRepository = userRepository;
    }

    public ApplicationUserDto getById(Long userId){
        if(userRepository.findById(userId) == null)
            throw new ResourceNotFoundException("UserId "+userId+" not found");
        ApplicationUser user =  userRepository.findById(userId).get();
        return dtoManager.convertToDto(user);
    }

    @Transactional
    public ApplicationUser createUser(ApplicationUser user){
        ApplicationUser save = this.userRepository.save(user);
        return save;
    }

    public ApplicationUser findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void delete(ApplicationUser user){
        userRepository.delete(user);
    }
}
