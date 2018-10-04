package com.example.easynotes.dto;

import java.util.Date;

public class NotesDto {

    private Long noteId;

    private String title;

    private String content;

    private ApplicationUserDto user;

    private Date updatedAt;

    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

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
