package com.example.easynotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notes")
public class Note extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @NotNull
    @Lob
    private String title;

    @NotNull
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private ApplicationUser user;

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    protected Note() { }

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

/*
    All your domain models must be annotated with @Entity annotation. It is used to mark the class as a persistent
    Java class.

    @Table annotation is used to provide the details of the table that this entity will be mapped to.

    @Id annotation is used to define the primary key.

    @GeneratedValue annotation is used to define the primary key generation strategy. In the above case, we have
    declared the primary key to be an Auto Increment field.

    @NotBlank annotation is used to validate that the annotated field is not null or empty.

    @Column annotation is used to define the properties of the column that will be mapped to the annotated field.
    You can define several properties like name, length, nullable, updateable etc.

    By default, a field named createdAt is mapped to a column named created_at in the database table. i.e. all
    camel cases are replaced with underscores.

    If you want to map the field to a different column, you can specify it using -

        @Column(name = "created_on")
        private String createdAt;

    @Temporal annotation is used with java.util.Date and java.util.Calendar classes. It converts the date and
    time values from Java Object to compatible database type and vice versa.

    @JsonIgnoreProperties annotation is a Jackson annotation. Spring Boot uses Jackson for Serializing and
    Deserializing Java objects to and from JSON.

    This annotation is used because we don’t want the clients of the rest api to supply the createdAt and
    updatedAt values. If they supply these values then we’ll simply ignore them. However, we’ll include these
    values in the JSON response.
     */
}
