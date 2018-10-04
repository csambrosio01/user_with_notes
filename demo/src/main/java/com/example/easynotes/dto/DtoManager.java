package com.example.easynotes.dto;

import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.model.Note;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class DtoManager {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoManager(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Note convertToEntity(NotesDto notesDto) throws ParseException {
        Note note = modelMapper.map(notesDto, Note.class);
        return note;
    }

    public NotesDto convertFromNoteToNotesDto(Note note){
        TypeMap<Note, NotesDto> typeMap = modelMapper.getTypeMap(Note.class, NotesDto.class);
        if (typeMap == null) {
            TypeMap<Note, NotesDto> typeMap1 = modelMapper.createTypeMap(Note.class, NotesDto.class);
            typeMap1.includeBase(Note.class, NotesDto.class);
            typeMap = typeMap1;
        }

        return typeMap.map(note);
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
