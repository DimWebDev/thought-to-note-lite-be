package com.thoughttonotelite.service;

import com.thoughttonotelite.model.Note;
import com.thoughttonotelite.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
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

    /**
     * Test for deleting a note by its ID.
     * <p>
     * This test verifies that the {@link NoteService#deleteNoteById(Long)} method correctly interacts with
     * the {@link NoteRepository#existsById(Object)} and {@link NoteRepository#deleteById(Object)} methods.
     * The repository's existsById method is mocked to return true, and the deleteById method is mocked to
     * perform the deletion. The test asserts that no exceptions are thrown during the deletion process.
     * </p>
     */
    @Test
    public void testDeleteNoteById() {
        // Mock the behavior of the NoteRepository to return true when existsById is called
        when(noteRepository.existsById(1L)).thenReturn(true);

        // Call the deleteNoteById method and assert that no exceptions are thrown
        assertDoesNotThrow(() -> noteService.deleteNoteById(1L));
    }

    /**
     * Test for retrieving all notes.
     * <p>
     * This test verifies that the {@link NoteService#getAllNotes()} method correctly interacts with
     * the {@link NoteRepository#findAll()} method to retrieve all notes. The repository's findAll method
     * is mocked to return a list of notes, and the test asserts that the returned list is not null and
     * contains the expected notes.
     * </p>
     */
    @Test
    public void testGetAllNotes() {
        // Create a list of mock Note objects
        List<Note> notes = Arrays.asList(
                new Note() {{ setId(1L); setTitle("Note 1"); setContent("Content 1"); }},
                new Note() {{ setId(2L); setTitle("Note 2"); setContent("Content 2"); }}
        );

        // Mock the behavior of the NoteRepository to return the list of notes when findAll is called
        when(noteRepository.findAll()).thenReturn(notes);

        // Call the getAllNotes method and assert the results
        List<Note> retrievedNotes = noteService.getAllNotes();

        assertNotNull(retrievedNotes);  // Verify that the retrieved list is not null
        assertEquals(2, retrievedNotes.size());  // Verify that the list contains the expected number of notes
        assertEquals("Note 1", retrievedNotes.get(0).getTitle());  // Verify the title of the first note
        assertEquals("Note 2", retrievedNotes.get(1).getTitle());  // Verify the title of the second note
    }

    /**
     * Test for retrieving a note by its ID.
     * <p>
     * This test verifies that the {@link NoteService#getNoteById(Long)} method correctly interacts with
     * the {@link NoteRepository#findById(Object)} method to retrieve a note by its ID. The repository's
     * findById method is mocked to return an Optional containing a note, and the test asserts that the
     * returned note is not null and contains the expected details.
     * </p>
     */
    @Test
    public void testGetNoteById() {
        // Create a mock Note object
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test Note");
        note.setContent("Test Content");

        // Mock the behavior of the NoteRepository to return the note when findById is called
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        // Call the getNoteById method and assert the results
        Note retrievedNote = noteService.getNoteById(1L);

        assertNotNull(retrievedNote);  // Verify that the retrieved note is not null
        assertEquals(1L, retrievedNote.getId());  // Verify that the ID is as expected
        assertEquals("Test Note", retrievedNote.getTitle());  // Verify that the title is as expected
        assertEquals("Test Content", retrievedNote.getContent());  // Verify that the content is as expected
    }

    /**
     * Test for searching notes by title.
     * <p>
     * This test verifies that the {@link NoteService#searchNotesByTitle(String)} method correctly interacts with
     * the {@link NoteRepository#findByTitleContainingIgnoreCase(String)} method to search for notes by title.
     * The repository's findByTitleContainingIgnoreCase method is mocked to return a list of notes, and the test
     * asserts that the returned list is not null and contains the expected notes.
     * </p>
     */
    @Test
    public void testSearchNotesByTitle() {
        // Create a list of mock Note objects
        List<Note> notes = Arrays.asList(
                new Note() {{ setId(1L); setTitle("Test Note 1"); setContent("Content 1"); }},
                new Note() {{ setId(2L); setTitle("Another Test Note"); setContent("Content 2"); }}
        );

        // Mock the behavior of the NoteRepository to return the list of notes when findByTitleContainingIgnoreCase is called
        when(noteRepository.findByTitleContainingIgnoreCase("Test")).thenReturn(notes);

        // Call the searchNotesByTitle method and assert the results
        List<Note> retrievedNotes = noteService.searchNotesByTitle("Test");

        assertNotNull(retrievedNotes);  // Verify that the retrieved list is not null
        assertEquals(2, retrievedNotes.size());  // Verify that the list contains the expected number of notes
        assertEquals("Test Note 1", retrievedNotes.get(0).getTitle());  // Verify the title of the first note
        assertEquals("Another Test Note", retrievedNotes.get(1).getTitle());  // Verify the title of the second note
    }

}