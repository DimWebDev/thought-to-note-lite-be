package com.thoughttonotelite.integration;

import com.thoughttonotelite.model.Note;
import com.thoughttonotelite.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class NoteIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("integrationdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NoteRepository noteRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // Ensures a clean schema for each test
    }

    @Test
    public void testCreateAndRetrieveNote() {
        // Ensure the database is clean before the test
        noteRepository.deleteAll();

        // Create a new Note object
        Note note = new Note();
        note.setTitle("Integration Test Note");
        note.setContent("Content for integration test.");

        // Set up HTTP headers with Basic Auth
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("yourUsername", "yourPassword"); // Ensure these credentials match your test configuration

        // Create HTTP entity with the Note object and headers
        HttpEntity<Note> request = new HttpEntity<>(note, headers);

        // Send POST request to create the note
        ResponseEntity<Note> postResponse = restTemplate.postForEntity(createURL("/api/notes"), request, Note.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Note createdNote = postResponse.getBody();
        assertThat(createdNote).isNotNull();
        assertThat(createdNote.getId()).isNotNull();
        assertThat(createdNote.getTitle()).isEqualTo("Integration Test Note");

        // Send GET request to retrieve the created note
        ResponseEntity<Note> getResponse = restTemplate.exchange(
                createURL("/api/notes/" + createdNote.getId()),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Note.class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Note retrievedNote = getResponse.getBody();
        assertThat(retrievedNote).isNotNull();
        assertThat(retrievedNote.getTitle()).isEqualTo("Integration Test Note");
    }

    // Helper method to construct the full URL for API requests
    private String createURL(String uri) {
        return "http://localhost:" + port + uri;
    }
}
