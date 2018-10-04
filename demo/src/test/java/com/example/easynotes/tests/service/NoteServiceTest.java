package com.example.easynotes.tests.service;


import com.example.easynotes.EasyNotesApplication;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.dto.NotesDto;
import com.example.easynotes.exception.NotesLimitReachedException;
import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.UserRepository;
import com.example.easynotes.service.NoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = EasyNotesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoManager dtoManager;

    //getAllNotesByUserId
    @Test
    public void whenValidUserId_thenReturnListOfNotesDto() throws ParseException {
        ApplicationUser user = createUser("ca", "passwo");

        NotesDto note = createNote(user);

        List<NotesDto> foundDto = noteService.getAllNotesByUserId(user.getId());

        assertEquals((note.getTitle()), foundDto.get(0).getTitle());
    }

    //getNoteByUserAndNoteId
    @Test
    public void whenValidUserIdAndNoteId_thenReturnNoteDto() throws ParseException {
        ApplicationUser user = createUser("cai", "passwor");

        NotesDto note = createNote(user);

        NotesDto notesDto = noteService.getNoteByUserIdAndNoteId(user.getId(), note.getNoteId());

        Note found = dtoManager.convertToEntity(notesDto);

        assertEquals((note.getTitle()), found.getTitle());
    }

    //updateNote
    @Test
    public void whenValidNoteId_thenUpdateNoteAndReturnNoteDto() throws ParseException{
        ApplicationUser user = createUser("caiooo", "passworddd");

        NotesDto note = createNote(user);

        Note noteRequest = new Note();
        noteRequest.setContent("testingg");
        noteRequest.setTitle("test 12");

        NotesDto notesRequestDto = dtoManager.convertFromNoteToNotesDto(noteRequest);

        NotesDto foundDto = noteService.updateNote(note.getNoteId(), notesRequestDto);

        assertEquals((noteRequest.getTitle()), foundDto.getTitle());
    }

    //deleteNote
    @Test
    public void whenValidNoteId_thenDeleteNote() throws ParseException{
        ApplicationUser user = createUser("caioo", "passwordd");

        NotesDto note = createNote(user);

        ResponseEntity<?> deleted = noteService.deleteNote(note.getNoteId());
    }


    //create more than 10 notes
    @Test
    public void whenCreatingMoreThan10Notes_thenReturnNotesLimitReachedException() throws NotesLimitReachedException, ParseException {
        ApplicationUser user = createUser("Caio", "password");

        for(int i = 0; i<11; i++) {
            NotesDto notesDto = new NotesDto();
            notesDto.setTitle("test " + i);
            notesDto.setContent("testing " + i);
            noteService.createNote(user.getId(), notesDto);
        }

        NotesDto writeNote = new NotesDto();
        writeNote.setTitle("test 11");
        writeNote.setContent("testing 11");
        noteService.createNote(user.getId(), writeNote);

        assertEquals(noteService.getNoteByUserIdAndNoteId(user.getId(), writeNote.getNoteId()), writeNote);
    }

    //createNote
    @Transactional
    public NotesDto createNote(ApplicationUser user) throws ParseException {
        NotesDto note = new NotesDto();
        note.setTitle("test 1");
        note.setContent("testing");
        return noteService.createNote(user.getId(), note);
    }

    @Transactional
    public ApplicationUser createUser(String username, String password){
        ApplicationUser user = new ApplicationUser();
        user.setPassword(password);
        user.setUsername(username);
        userRepository.save(user);
        return user;
    }
}
