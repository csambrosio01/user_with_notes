package com.example.easynotes.service;

import com.example.easynotes.dto.ApplicationUserDto;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.dto.NotesDto;
import com.example.easynotes.exception.NotesLimitReachedException;
import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    private final UserService userService;

    private final DtoManager dtoManager;

    private final int MAX = 10;


    @Autowired
    public NoteService(NoteRepository noteRepository, UserService userService, DtoManager dtoManager){
        this.noteRepository = noteRepository;
        this.userService = userService;
        this.dtoManager = dtoManager;
    }

    public List<Note> getAllNotesByUser(ApplicationUser user) {
        List<Note> savedNotes = noteRepository.findAllByUser(user);
        return savedNotes;
    }

    public List<NotesDto> getAllNotesByUserId(Long userId) throws ParseException {
        if (userService.getById(userId) == null) {
            throw new ResourceNotFoundException("UserId "+userId+" not found");
        }
        ApplicationUserDto user = userService.getById(userId);
        List<Note> notes = noteRepository.findAllByUser(dtoManager.convertToEntity(user));
        for(int i = 0; i < (notes.size()/2); i++){
            Note aux = notes.get(i);
            notes.set(i, notes.get(notes.size() - 1 - i));
            notes.set(notes.size() - 1 - i, aux);
        }
        return notes.stream().map(dtoManager::convertFromNoteToNotesDto).collect(Collectors.toList());
    }

    @Transactional
    public NotesDto createNote(Long userId, NotesDto notesDto) throws ParseException {
        ApplicationUser user = dtoManager.convertToEntity(userService.getById(userId));
        int numberOfNotes = getAllNotesByUser(user).size();
        if(user != null &&  numberOfNotes < MAX){
            Note note = dtoManager.convertToEntity(notesDto);
            note.setUser(user);
            noteRepository.save(note);
            return dtoManager.convertFromNoteToNotesDto(note);
        }
        throw new NotesLimitReachedException("Can not add a new Note");
    }
    public NotesDto getNoteByUserIdAndNoteId(Long userId, Long noteId) throws ParseException {
        ApplicationUserDto user = userService.getById(userId);
        if (user != null) {
            Note saved = noteRepository.findByUserAndNoteId(dtoManager.convertToEntity(user), noteId);
            if (saved != null) return dtoManager.convertFromNoteToNotesDto(saved);
        }
        throw new ResourceNotFoundException("UserId " + userId + " not found");
    }

    public NotesDto updateNote(Long noteId, @Valid NotesDto noteRequest) throws ParseException{
        Optional<Note> note = noteRepository.findById(noteId);
        if(!note.isPresent()) {
            throw new ResourceNotFoundException();
        }
        note.get().setContent(noteRequest.getContent());
        note.get().setTitle(noteRequest.getTitle());
        noteRepository.save(note.get());
        return dtoManager.convertFromNoteToNotesDto(note.get());
    }

    public ResponseEntity<?> deleteNote(Long noteId) {
        return noteRepository.findById(noteId).map(note -> {noteRepository.delete(note);return ResponseEntity.ok().build();}).orElseThrow(() -> new ResourceNotFoundException("NoteId "+noteId+ " not found"));
    }
}
