package com.thoughttonotelite.repository;

import com.thoughttonotelite.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Note} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing CRUD operations and additional
 * JPA-related methods for the {@link Note} entity. The interface is annotated with {@link Repository},
 * indicating that it is a Spring Data repository.
 * </p>
 * <p>x
 * By extending {@link JpaRepository}, this interface inherits several methods for working with {@link Note}
 * persistence, including methods for saving, deleting, and finding notes. Additionally, custom query methods
 * can be defined by following Spring Data JPA naming conventions.
 * </p>
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    /**
     * Finds notes by their title, where the title contains the specified keyword, ignoring case.
     * <p>
     * This method follows the Spring Data JPA query method naming conventions. By defining the method
     * with the name {@code findByTitleContainingIgnoreCase}, Spring Data JPA will automatically
     * generate the necessary query to find all {@link Note} entities where the title contains the
     * specified keyword, case-insensitively.
     * </p>
     *
     * @param title the keyword to search for in the title
     * @return a list of {@link Note} entities with titles containing the specified keyword, ignoring case
     */
    List<Note> findByTitleContainingIgnoreCase(String title);
}