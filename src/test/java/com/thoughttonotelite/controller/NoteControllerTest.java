package com.thoughttonotelite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughttonotelite.model.Note;
import com.thoughttonotelite.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for the {@link NoteController} class.
 * <p>
 * This test class is responsible for testing the web layer of the application, specifically the NoteController,
 * which handles HTTP requests related to the {@link Note} entity. The tests focus on verifying the correctness
 * of the controller's behavior, including security aspects such as authentication and CSRF protection.
 * </p>
 * <p>
 * The {@link WebMvcTest} annotation is used to configure the test environment, focusing only on the web layer
 * and loading only the specified controller and its dependencies. The service layer is mocked to isolate
 * the controller logic from business logic.
 * </p>
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    /**
     * MockMvc is a Spring MVC test framework that provides support for testing Spring MVC controllers.
     * It allows sending mock HTTP requests to a controller and verifying the response.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * MockBean is used to provide a mock implementation of the NoteService.
     * This allows us to isolate the controller from the service layer during testing.
     */
    @MockBean
    private NoteService noteService;

    /**
     * Test for retrieving all notes.
     * <p>
     * This test verifies that the controller correctly handles a GET request to retrieve all notes.
     * The test uses {@link WithMockUser} to simulate an authenticated user with the role "USER".
     * The {@link NoteService} is mocked to return a predefined list of notes, and the response is
     * verified to ensure it matches the expected result.
     * </p>
     *
     * @throws Exception if any error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "yourUsername", roles = {"USER"})
    public void testGetAllNotes() throws Exception {
        // Create a mock note object to be returned by the service
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Test Content");

        // Mock the behavior of the NoteService to return a list containing the mock note
        Mockito.when(noteService.getAllNotes()).thenReturn(Collections.singletonList(note));

        // Perform a GET request to the /api/notes endpoint and verify the response
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Expect JSON content type
                .andExpect(jsonPath("$[0].title").value("Test Note"));  // Expect the first note's title to be "Test Note"
    }

    /**
     * Test for creating a new note.
     * <p>
     * This test verifies that the controller correctly handles a POST request to create a new note.
     * The test uses {@link WithMockUser} to simulate an authenticated user with the role "USER".
     * The {@link csrf()} method is used to add a CSRF token to the request, which is required by Spring Security
     * to prevent CSRF attacks. The {@link NoteService} is mocked to return the created note, and the response
     * is verified to ensure it matches the expected result.
     * </p>
     *
     * @throws Exception if any error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "yourUsername", roles = {"USER"})
    public void testCreateNote() throws Exception {
        // Create a mock note object to be used in the request and returned by the service
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Test Content");

        // Mock the behavior of the NoteService to return the created note
        Mockito.when(noteService.createNote(Mockito.any(Note.class))).thenReturn(note);

        // Perform a POST request to the /api/notes endpoint with the note object and verify the response
        mockMvc.perform(post("/api/notes")
                        .with(csrf())  // Add CSRF token to the request to pass CSRF protection
                        .contentType(MediaType.APPLICATION_JSON)  // Set the content type to JSON
                        .content(new ObjectMapper().writeValueAsString(note)))  // Set the request body to the serialized note object
                .andExpect(status().isCreated())  // Expect HTTP 201 Created status
                .andExpect(jsonPath("$.title").value("Test Note"));  // Expect the created note's title to be "Test Note"
    }

    /**
     * Test for updating an existing note.
     * <p>
     * This test verifies that the controller correctly handles a PUT request to update an existing note.
     * The test uses {@link WithMockUser} to simulate an authenticated user with the role "USER".
     * The {@link csrf()} method is used to add a CSRF token to the request, which is required by Spring Security
     * to prevent CSRF attacks. The {@link NoteService} is mocked to return the updated note, and the response
     * is verified to ensure it matches the expected result.
     * </p>
     *
     * @throws Exception if any error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "yourUsername", roles = {"USER"})
    public void testUpdateNote() throws Exception {
        // Create a mock note object to be used in the request and returned by the service
        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Note");
        updatedNote.setContent("Updated Content");

        // Mock the behavior of the NoteService to return the updated note
        Mockito.when(noteService.updateNote(Mockito.anyLong(), Mockito.any(Note.class))).thenReturn(updatedNote);

        // Perform a PUT request to the /api/notes/{id} endpoint with the updated note details and verify the response
        mockMvc.perform(put("/api/notes/{id}", 1L)
                        .with(csrf())  // Add CSRF token to the request to pass CSRF protection
                        .contentType(MediaType.APPLICATION_JSON)  // Set the content type to JSON
                        .content(new ObjectMapper().writeValueAsString(updatedNote)))  // Set the request body to the serialized updated note object
                .andExpect(status().isOk())  // Expect HTTP 200 OK status
                .andExpect(jsonPath("$.title").value("Updated Note"))  // Expect the updated note's title to be "Updated Note"
                .andExpect(jsonPath("$.content").value("Updated Content"));  // Expect the updated note's content to be "Updated Content"
    }

    /**
     * Test for deleting an existing note.
     * <p>
     * This test verifies that the controller correctly handles a DELETE request to delete an existing note.
     * The test uses {@link WithMockUser} to simulate an authenticated user with the role "USER".
     * The {@link csrf()} method is used to add a CSRF token to the request, which is required by Spring Security
     * to prevent CSRF attacks. The {@link NoteService} is mocked to do nothing when deleting the note, and the response
     * is verified to ensure it matches the expected result.
     * </p>
     *
     * @throws Exception if any error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "yourUsername", roles = {"USER"})
    public void testDeleteNote() throws Exception {
        // Mock the behavior of the NoteService to do nothing when deleting a note
        Mockito.doNothing().when(noteService).deleteNoteById(Mockito.anyLong());

        // Perform a DELETE request to the /api/notes/{id} endpoint and verify the response
        mockMvc.perform(delete("/api/notes/{id}", 1L)
                        .with(csrf()))  // Add CSRF token to the request to pass CSRF protection
                .andExpect(status().isNoContent());  // Expect HTTP 204 No Content status
    }

    /**
     * Test for retrieving an individual note by its ID.
     * <p>
     * This test verifies that the controller correctly handles a GET request to retrieve a note by its ID.
     * The test uses {@link WithMockUser} to simulate an authenticated user with the role "USER".
     * The {@link NoteService} is mocked to return the requested note, and the response is
     * verified to ensure it matches the expected result.
     * </p>
     *
     * @throws Exception if any error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "yourUsername", roles = {"USER"})
    public void testGetNoteById() throws Exception {
        // Create a mock note object to be returned by the service
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Test Content");

        // Mock the behavior of the NoteService to return the note
        Mockito.when(noteService.getNoteById(Mockito.anyLong())).thenReturn(note);

        // Perform a GET request to the /api/notes/{id} endpoint and verify the response
        mockMvc.perform(get("/api/notes/{id}", 1L))
                .andExpect(status().isOk())  // Expect HTTP 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Expect JSON content type
                .andExpect(jsonPath("$.title").value("Test Note"))  // Expect the note's title to be "Test Note"
                .andExpect(jsonPath("$.content").value("Test Content"));  // Expect the note's content to be "Test Content"
    }


    /**
     * Test for searching notes by title.
     * <p>
     * This test verifies that the controller correctly handles a GET request to search for notes by title.
     * The test uses {@link WithMockUser} to simulate an authenticated user with the role "USER".
     * The {@link NoteService} is mocked to return a list of notes matching the search criteria, and the response
     * is verified to ensure it matches the expected result.
     * </p>
     *
     * @throws Exception if any error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "yourUsername", roles = {"USER"})
    public void testSearchNotesByTitle() throws Exception {
        // Create a mock note object to be returned by the service
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Test Content");

        // Mock the behavior of the NoteService to return a list containing the mock note
        Mockito.when(noteService.searchNotesByTitle(Mockito.anyString())).thenReturn(Collections.singletonList(note));

        // Perform a GET request to the /api/notes/search endpoint with the title parameter and verify the response
        mockMvc.perform(get("/api/notes/search")
                        .param("title", "Test"))
                .andExpect(status().isOk())  // Expect HTTP 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Expect JSON content type
                .andExpect(jsonPath("$[0].title").value("Test Note"))  // Expect the first note's title to be "Test Note"
                .andExpect(jsonPath("$[0].content").value("Test Content"));  // Expect the first note's content to be "Test Content"
    }

}