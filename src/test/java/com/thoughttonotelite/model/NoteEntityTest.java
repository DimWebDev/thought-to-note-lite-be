package com.thoughttonotelite.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the Note entity.
 * This class contains tests to verify the persistence and integrity of the Note entity.
 */
@SpringBootTest
@Testcontainers
class NoteEntityTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("thoughttnotelitedb")
            .withUsername("postgres")
            .withPassword("postgres");

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Dynamically sets the Spring datasource properties to match the Testcontainers PostgreSQL instance.
     *
     * @param registry the dynamic property registry
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // or your preferred setting
    }

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

        // Allow for a slight time difference due to processing time
        Duration difference = Duration.between(note.getCreatedAt(), note.getUpdatedAt());
        assertThat(difference).isLessThanOrEqualTo(Duration.ofMillis(10)); // Allowable difference of 10 milliseconds
    }
}
