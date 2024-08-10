package com.thoughttonotelite.unit.model;

import com.thoughttonotelite.model.Note;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the Note entity.
 * This class contains tests to verify the persistence and integrity of the Note entity.
 */
@SpringBootTest  // Load the full application context
class NoteEntityTest {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Test method to verify the persistence of a Note entity.
     * This method creates a Note instance, persists it, and verifies that the ID, createdAt, and updatedAt fields are correctly set.
     */
    @Test
    @Transactional  // Ensure the test runs in a transaction
    void testNoteEntityPersistence() {
        // Create a new Note instance
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("This is a test note.");

        // Persist the Note entity
        entityManager.persist(note);
        entityManager.flush();  // Force synchronization with the database

        // Verify that the ID has been generated and assigned
        assertThat(note.getId()).isNotNull();

        // Verify that createdAt and updatedAt are set
        assertThat(note.getCreatedAt()).isNotNull();
        assertThat(note.getUpdatedAt()).isNotNull();

        // Allow for a small time difference due to processing time
        Duration difference = Duration.between(note.getCreatedAt(), note.getUpdatedAt());
        assertThat(difference).isLessThanOrEqualTo(Duration.ofMillis(10)); // Allowable difference of 10 milliseconds
    }
}