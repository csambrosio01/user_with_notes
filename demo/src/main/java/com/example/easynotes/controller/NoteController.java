package com.example.easynotes.controller;

import com.example.easynotes.dto.ApplicationUserDto;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.dto.NotesDto;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.model.Note;
import com.example.easynotes.service.NoteService;
import com.example.easynotes.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    private DtoManager dtoManager = new DtoManager();

    //Get All Notes
    @GetMapping("/{userId}/notes")
    public List<NotesDto> getAllNotesByUserId(@PathVariable (value = "userId") Long userId) throws ParseException {
        if (userService.getById(userId) == null) {
            throw new ResourceNotFoundException("UserId "+userId+" not found");
        }
        ApplicationUserDto user = userService.getById(userId);
        List<Note> notes = noteService.getAllNotesByUser(dtoManager.convertToEntity(user));
        return notes.stream().map(note -> dtoManager.convertToDto(note)).collect(Collectors.toList());
    }


    //Create a new Note
    @PostMapping("/{userId}/notes")
    public NotesDto createNote(@PathVariable (value = "userId") Long userId, @Valid @RequestBody NotesDto notesDto) throws ParseException {
        ApplicationUserDto user = userService.getById(userId);
        if(user != null){
            Note note = dtoManager.convertToEntity(notesDto);
            note.setUser(dtoManager.convertToEntity(user));
            noteService.createNote(note);
            return dtoManager.convertToDto(note);
        }
        throw new ResourceNotFoundException("UserId "+userId+" not found");

    }

    //Get a Single Note
    @GetMapping("/{userId}/notes/{noteId}")
    public NotesDto getNoteByUserIdAndNoteId(@PathVariable(value = "userId") Long userId, @PathVariable(value = "noteId") Long noteId) throws ParseException {
        ApplicationUserDto  user = userService.getById(userId);
        if(user != null){
            Note saved = noteService.getNoteByUserAndNoteId(dtoManager.convertToEntity(user), noteId);
            if(saved != null) return dtoManager.convertToDto(saved);
        }
        throw new ResourceNotFoundException("UserId "+userId+" not found");

    }

    //Update a Note
    @PutMapping("/notes/{noteId}")
    public void updateNote(@PathVariable (value = "noteId") Long noteId, @Valid @RequestBody NotesDto noteRequest) throws ParseException {
        Optional<Note> note = noteService.getNoteByNoteId(noteId);
        note.get().setTitle(noteRequest.getTitle());
        note.get().setContent(noteRequest.getContent());
        noteService.updateNote(note.get());
    }

    //Delete a Note
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable (value = "noteId") Long noteId){
        return noteService.getNoteByNoteId(noteId).map(note -> {noteService.delete(note);return ResponseEntity.ok().build();}).orElseThrow(() -> new ResourceNotFoundException("NoteId "+noteId+ " not found"));
    }
}

/*
@RestController annotation is a combination of Spring’s @Controller and @ResponseBody annotations.

The @Controller annotation is used to define a controller and the @ResponseBody annotation is used to
indicate that the return value of a method should be used as the response body of the request.

@RequestMapping("/api") declares that the url for all the apis in this controller will start with /api.

Let’s now look at the implementation of all the apis one by one.
 */