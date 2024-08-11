package com.thoughttonotelite.service;

import com.thoughttonotelite.model.Note;
import com.thoughttonotelite.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link NoteService} class.
 * <p>
 * This test class is responsible for verifying the business logic in the NoteService class, which handles
 * operations related to the {@link Note} entity. The tests focus on ensuring that the service methods
 * interact correctly with the {@link NoteRepository} and return the expected results.
 * </p>
 * <p>
 * The {@link MockitoExtension} is used to initialize mocks and inject them into the service being tested.
 * This allows for isolated testing of the service layer without depending on the actual database or repository.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    /**
     * The NoteRepository is mocked to simulate database operations without performing real database interactions.
     */
    @Mock
    private NoteRepository noteRepository;

    /**
     * The NoteService is the service being tested. Mocks are injected into this service
     * to isolate it from dependencies such as the NoteRepository.
     */
    @InjectMocks
    private NoteService noteService;

    /**
     * Test for creating a new note.
     * <p>
     * This test verifies that the {@link NoteService#createNote(Note)} method correctly interacts with
     * the {@link NoteRepository#save(Object)} method to save a new note and return it. The repository's
     * save method is mocked to return the provided note, and the test asserts that the returned note
     * is not null and has the expected title.
     * </p>
     */
    @Test
    public void testCreateNote() {
        // Create a mock Note object
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Test Content");

        // Mock the behavior of the NoteRepository to return the note when save is called
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        // Call the createNote method and assert the results
        Note createdNote = noteService.createNote(note);

        assertNotNull(createdNote);  // Verify that the created note is not null
        assertEquals("Test Note", createdNote.getTitle());  // Verify that the title is as expected
    }

    /**
     * Test for updating an existing note.
     * <p>
     * This test verifies that the {@link NoteService#updateNote(Long, Note)} method correctly updates an existing note.
     * The repository's findById method is mocked to return an existing note, and the save method is mocked to
     * return the updated note. The test then asserts that the note was updated with the new title and content.
     * </p>
     */
    @Test
    public void testUpdateNote() {
        // Create a mock existing Note object
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Old Title");
        note.setContent("Old Content");

        // Create a mock Note object with updated details
        Note updatedNoteDetails = new Note();
        updatedNoteDetails.setTitle("New Title");
        updatedNoteDetails.setContent("New Content");

        // Mock the behavior of the NoteRepository
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));  // Mock finding the note by ID
        when(noteRepository.save(any(Note.class))).thenReturn(updatedNoteDetails);  // Mock saving the updated note

        // Call the updateNote method and assert the results
        Note updatedNote = noteService.updateNote(1L, updatedNoteDetails);

        assertEquals("New Title", updatedNote.getTitle());  // Verify that the title was updated
        assertEquals("New Content", updatedNote.getContent());  // Verify that the content was updated
    }

    // Add more tests for deleteNoteById, getAllNotes, getNoteById, etc.
}