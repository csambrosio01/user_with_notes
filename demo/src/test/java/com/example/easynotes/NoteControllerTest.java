package com.example.easynotes;

import com.example.easynotes.controller.NoteController;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.dto.NotesDto;
import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.model.Note;
import com.example.easynotes.service.UserDetailsServiceImpl;
import com.example.easynotes.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.List;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = EasyNotesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NoteController noteController;

    @MockBean
    private UserService userService;

    private DtoManager dtoManager = new DtoManager();

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userService);

    @Test
    public void getNotesByUserId() throws ParseException, Exception {
        ApplicationUser user = createUser();

        UserDetails userDetails = userDetailsService.loadUserByUsername("caio");

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        Note note = createNote(user);

        NotesDto notesDto = dtoManager.convertToDto(note);
        List<NotesDto> allNotes = singletonList(notesDto);

        given(noteController.getAllNotesByUserId(user.getId()))
                .willReturn(allNotes);

        mvc.perform(get("/1/notes")
                .with(user("root").password("mysql"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(note.getTitle())));
    }

    @Test
    public void getNoteByUserAndNoteId() throws Exception, ParseException {
        ApplicationUser user = createUser();

        UserDetails userDetails = userDetailsService.loadUserByUsername("caio");

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        Note note = createNote(user);

        NotesDto notesDto = dtoManager.convertToDto(note);

        given(noteController.getNoteByUserIdAndNoteId(user.getId(), note.getNoteId()))
                .willReturn(notesDto);

        mvc.perform(get("/1/notes/1")
                .with(user("blaze")
                        .password("Q1w2e3r4"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is(note.getTitle())));
    }


    //Put
    @Test
    public void updateNote() throws ParseException, Exception {
        ApplicationUser user = createUser();

        UserDetails userDetails = userDetailsService.loadUserByUsername("caio");

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        Note note = createNote(user);

        Note noteToUpdate = new Note();
        note.setTitle("test 2");
        note.setContent("testingg");
        note.setUser(user);

        NotesDto notesDto = dtoManager.convertToDto(note);

        NotesDto noteUpdatedDto = dtoManager.convertToDto(noteToUpdate);

        given(noteController.updateNote(note.getNoteId(), noteUpdatedDto))
                .willReturn(notesDto);

        mvc.perform(put("/notes/1")
                .with(user("blaze")
                        .password("Q1w2e3r4"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title", is(noteUpdatedDto.getTitle())));
    }


    //Delete
    @Test
    public void removeNoteByNoteId() throws ParseException, Exception {
        ApplicationUser user = createUser();

        UserDetails userDetails = userDetailsService.loadUserByUsername("caio");

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        Note note = createNote(user);

        NotesDto notesDto = dtoManager.convertToDto(note);

        mvc.perform(delete("/notes/1")
                .with(user("blaze")
                        .password("Q1w2e3r4"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public ApplicationUser createUser(){
        ApplicationUser user = new ApplicationUser();
        user.setUsername("caio");
        user.setPassword("test");
        return user;
    }

    public Note createNote(ApplicationUser user){
        Note note = new Note();
        note.setTitle("test 1");
        note.setContent("testing");
        note.setUser(user);
        return note;
    }

}
