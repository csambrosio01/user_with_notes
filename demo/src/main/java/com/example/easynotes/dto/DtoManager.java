package com.example.easynotes.dto;

import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.model.Note;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

public class DtoManager {
    @Autowired
    private final ModelMapper modelMapper = new ModelMapper();

    public Note convertToEntity(NotesDto notesDto) throws ParseException {
        Note note = modelMapper.map(notesDto, Note.class);
        return note;
    }

    public NotesDto convertToDto(Note note){
        NotesDto noteDto = modelMapper.map(note, NotesDto.class);
        return noteDto;
    }

    public ApplicationUser convertToEntity(ApplicationUserDto userDto) throws ParseException {
        ApplicationUser user = modelMapper.map(userDto, ApplicationUser.class);
        return user;
    }

    public ApplicationUserDto convertToDto(ApplicationUser user){
        ApplicationUserDto userDto = modelMapper.map(user, ApplicationUserDto.class);
        return userDto;
    }
}
