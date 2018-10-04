package com.example.easynotes.controller;

import com.example.easynotes.dto.NotesDto;
import com.example.easynotes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    //Get All Notes
    @GetMapping("/{userId}/notes")
    public List<NotesDto> getAllNotesByUserId(@PathVariable (value = "userId") Long userId) throws ParseException {
        return noteService.getAllNotesByUserId(userId);
    }

    //Create a new Note
    @PostMapping("/{userId}/notes")
    public NotesDto createNote(@PathVariable (value = "userId") Long userId, @Valid @RequestBody NotesDto notesDto) throws ParseException {
        return noteService.createNote(userId, notesDto);
    }

    //Get a Single Note
    @GetMapping("/{userId}/notes/{noteId}")
    public NotesDto getNoteByUserIdAndNoteId(@PathVariable(value = "userId") Long userId, @PathVariable(value = "noteId") Long noteId) throws ParseException {
        return noteService.getNoteByUserIdAndNoteId(userId, noteId);
    }

    //Update a Note
    @PutMapping("/notes/{noteId}")
    public NotesDto updateNote(@PathVariable (value = "noteId") Long noteId, @Valid @RequestBody NotesDto noteRequest) throws ParseException {
        return noteService.updateNote(noteId, noteRequest);
    }

    //Delete a Note
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable (value = "noteId") Long noteId){
        return noteService.deleteNote(noteId);
    }
}

/*
@RestController annotation is a combination of Spring’s @Controller and @ResponseBody annotations.

The @Controller annotation is used to define a controller and the @ResponseBody annotation is used to
indicate that the return value of a method should be used as the response body of the request.

@RequestMapping("/api") declares that the url for all the apis in this controller will start with /api.

Let’s now look at the implementation of all the apis one by one.
 */