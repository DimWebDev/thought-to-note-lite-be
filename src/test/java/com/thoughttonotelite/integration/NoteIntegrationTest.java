package com.thoughttonotelite.integration;

import com.thoughttonotelite.model.Note;
import com.thoughttonotelite.repository.NoteRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class NoteIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NoteRepository noteRepository;

    @BeforeAll
    public static void setup() {
        postgresqlContainer.start();
        System.setProperty("spring.datasource.url", postgresqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresqlContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresqlContainer.getPassword());
    }

    @AfterAll
    public static void tearDown() {
        postgresqlContainer.stop();
    }

    @Test
    public void testCreateAndRetrieveNote() {
        // Create a new note
        Note note = new Note();
        note.setTitle("Integration Test Note");
        note.setContent("Content for integration test.");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("yourUsername", "yourPassword"); // Ensure authentication

        HttpEntity<Note> request = new HttpEntity<>(note, headers);

        ResponseEntity<Note> response = restTemplate.postForEntity(createURL("/api/notes"), request, Note.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Note createdNote = response.getBody();
        assertThat(createdNote).isNotNull();
        assertThat(createdNote.getId()).isNotNull();
        assertThat(createdNote.getTitle()).isEqualTo("Integration Test Note");

        // Retrieve the created note
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

    private String createURL(String uri) {
        return "http://localhost:" + port + uri;
    }
}
