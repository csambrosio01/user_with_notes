package com.example.easynotes.repository;

import com.example.easynotes.model.Note;
import com.example.easynotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository <Note, Long> {
    List<Note> findByUser(User user);

    @Query
    Note findByUserAndNoteId(User user, Long id);
}

/*
Note that, we have annotated the interface with @Repository annotation. This tells Spring to bootstrap
the repository during component scan.

Great! That is all you have to do in the repository layer. You will now be able to use JpaRepository’s
methods like save(), findOne(), findAll(), count(), delete() etc.

You don’t need to implement these methods. They are already implemented by Spring Data JPA’s SimpleJpaRepository.
This implementation is plugged in by Spring automatically at runtime.

Checkout all the methods available from SimpleJpaRepository’s documentation.

Spring Data JPA has a bunch of other interesting features like Query methods (dynamically creating queries
based on method names), Criteria API, Specifications, QueryDsl etc.

I strongly recommend you to checkout the Spring Data JPA’s documentation to learn more.
 */