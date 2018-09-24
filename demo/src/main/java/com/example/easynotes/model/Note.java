package com.example.easynotes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "notes")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)


public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Column(nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @PreUpdate
    void preUpdate() {
        this.updatedAt = new Date();
    }

    @PrePersist
    void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
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

    //Getters and Setters


    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
