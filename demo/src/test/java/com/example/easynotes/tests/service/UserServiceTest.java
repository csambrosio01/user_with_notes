package com.example.easynotes.tests.service;

import com.example.easynotes.EasyNotesApplication;
import com.example.easynotes.dto.ApplicationUserDto;
import com.example.easynotes.dto.DtoManager;
import com.example.easynotes.model.ApplicationUser;
import com.example.easynotes.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = EasyNotesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DtoManager dtoManager;


    //getById
    @Test
    public void whenValidUserId_thenReturnUserDto(){
        ApplicationUserDto user = createUser("caio", "password");

        assertEquals(user.getId(), userService.getById(user.getId()).getId());
    }

    //findByUsername
    @Test
    public void whenValidUsername_thenReturnUser_ifUserExists(){
        ApplicationUserDto user = createUser("caio1", "password1");

        assertEquals(user.getId(), userService.findByUsername("caio1").getId());
    }


    //deleteUser
    @Test
    public void whenValidUserId_thenDeleteUser() throws ParseException {
        ApplicationUserDto user = createUser("caio2", "password2");

        ResponseEntity<?> deleted = userService.deleteUser(user.getId());

        ResponseEntity<?> expected = ResponseEntity.ok().build();

        assertEquals(deleted.getStatusCode(), expected.getStatusCode());
    }



    //updateUser
    @Test
    public void whenValidUserId_thenUpdateUser() throws ParseException {
        ApplicationUserDto user = createUser("caio3", "password3");

        ApplicationUserDto userRequest = createUser("caio4", "password4");

        user = userService.updateUser(user.getId(), dtoManager.convertToEntity(userRequest));

        assertEquals(userRequest.getUsername(), user.getUsername());
    }


    //createUser
    private ApplicationUserDto createUser(String username, String password){
        ApplicationUser user = new ApplicationUser();
        user.setUsername(username);
        user.setPassword(password);
        return userService.createUser(user);
    }


}
