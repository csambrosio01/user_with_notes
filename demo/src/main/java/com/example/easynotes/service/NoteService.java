package com.example.easynotes.service;

import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public Optional<Note> getNoteByNoteId(Long noteId){
        Optional<Note> save = noteRepository.findById(noteId);
        return save;
    }

    public Note createNote(Note note){
        Note savedNote = noteRepository.save(note);
        return savedNote;
    }

    public Note getNoteByUserAndNoteId(ApplicationUser user, Long noteId){
        Note savedNote = noteRepository.findByUserAndNoteId(user, noteId);
        return savedNote;
    }

    public void delete(Note note) {
        noteRepository.delete(note);
    }

    public List<Note> getAllNotesByUser(ApplicationUser user) {
        List<Note> savedNotes = noteRepository.findAllByUser(user);
        return savedNotes;
    }

    public void updateNote(Note note) {
        noteRepository.save(note);
    }
}
