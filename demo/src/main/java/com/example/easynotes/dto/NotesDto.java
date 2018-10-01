package com.example.easynotes.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class NotesDto {
    @Autowired
    private ModelMapper modelMapper;

    private Long noteId;

    private String title;

    private String content;

    private ApplicationUserDto user;

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ApplicationUserDto getUser() {
        return user;
    }

    public void setUser(ApplicationUserDto user) {
        this.user = user;
    }
}
