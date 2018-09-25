package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;

    //Get All Note
    @GetMapping("/users/{userId}/notes")
    public Note getAllNotesByUserId(@PathVariable (value = "userId") Long userId){
        return noteRepository.findByUserId(userId);
    }


    //Create a new Note
    @PostMapping("/users/{userId}/notes ")
    public Note createNote(@PathVariable (value = "userId") Long userId, @Valid @RequestBody Note note){
        Note savedNote = userRepository.findById(userId).map(user -> {note.setUser(user); return noteRepository.save(note);}).orElseThrow(() -> new ResourceNotFoundException("UserId "+ userId+ " not found"));
        return savedNote;
    }

    //Get a Single Note
    @GetMapping("/users/{userId}/notes/{noteId}")
    public Optional<Note> getNoteByUserIdAndNoteId(@PathVariable(value = "userId") Long userId, @PathVariable(value = "noteId") Long noteId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("UserId "+userId+" not found");
        }

        return noteRepository.findById(noteId);
    }

    //Update a Note
    @PutMapping("/users/{userId}/notes/{noteId}")
    public Note updateNote(@PathVariable(value = "userId") Long userId, @PathVariable (value = "noteId") Long noteId, @Valid @RequestBody Note noteRequest){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("UserId "+userId+" not found");
        }

        return noteRepository.findById(noteId).map(note -> {note.setTitle(noteRequest.getTitle());note.setContent(noteRequest.getContent());return noteRepository.save(note); }).orElseThrow(() -> new ResourceNotFoundException("NoteId "+noteId+" not found"));

    }

    //Delete a Note
    @DeleteMapping("/users/{userId}/notes/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "userId") Long userId, @PathVariable (value = "noteId") Long noteId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("UserId "+userId+ " not found");
        }

        return noteRepository.findById(noteId).map(note -> {noteRepository.delete(note);return ResponseEntity.ok().build();}).orElseThrow(() -> new ResourceNotFoundException("NoteId "+noteId+ " not found"));

    }
}

/*
@RestController annotation is a combination of Spring’s @Controller and @ResponseBody annotations.

The @Controller annotation is used to define a controller and the @ResponseBody annotation is used to
indicate that the return value of a method should be used as the response body of the request.

@RequestMapping("/api") declares that the url for all the apis in this controller will start with /api.

Let’s now look at the implementation of all the apis one by one.
 */