package com.example.easynotes.service;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.NoteRepository;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    private final UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository){
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public Optional<Note> getNoteByNoteId(Long noteId){
        Optional<Note> save = noteRepository.findById(noteId);
        return save;
    }

    public Note createNote(Note note){
        return noteRepository.save(note);
    }



    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public List<Note> getAllNotesByUser(User user) {
        List<Note> savedNotes = noteRepository.findByUser(user);
        return savedNotes;
    }

    public Note getNoteByUserIdAndNoteId(User user, Long noteId) {
        Note save = noteRepository.findByUserAndNoteId(user, noteId);
        return save;
    }
}
