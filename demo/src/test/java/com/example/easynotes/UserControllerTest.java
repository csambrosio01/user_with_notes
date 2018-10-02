package com.example.easynotes;

import com.example.easynotes.controller.UserController;
import com.example.easynotes.dto.ApplicationUserDto;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.model.ApplicationUser;

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
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = EasyNotesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserController userController;

    DtoManager dtoManager = new DtoManager();

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userService);

    //delete
    @Test
    public void deleteUser() throws Exception {
        ApplicationUser user = createUser();

        UserDetails userDetails = userDetailsService.loadUserByUsername("caio");

        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        mvc.perform(delete("/users/"+user.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    //put
    @Test
    public void updateUser() throws ParseException, Exception {
        ApplicationUser user = createUser();

        ApplicationUser userRequest = new ApplicationUser();
        userRequest.setPassword("caioo");
        userRequest.setUsername("testt");

        ApplicationUserDto userRequestDto = dtoManager.convertToDto(userRequest);

        given(userController.updateUser(user.getId(), userRequest))
                .willReturn(userRequestDto);

        mvc.perform(put("/users/"+ user.getId())
                .with(user("blaze").password("Q1w2e3r4"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username", is(userRequestDto.getUsername())));
    }


    public ApplicationUser createUser(){
        ApplicationUser user = new ApplicationUser();
        user.setUsername("caio");
        user.setPassword("test");
        return user;
    }

}
