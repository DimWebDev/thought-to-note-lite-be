package com.thoughttonotelite.controller;

import com.thoughttonotelite.model.Note;
import com.thoughttonotelite.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link Note} entities.
 * <p>
 * This controller provides endpoints to create, update, delete, retrieve, and search for notes.
 * Each endpoint corresponds to a specific operation that can be performed on notes.
 * The controller uses the {@link NoteService} to perform the necessary business logic and interact
 * with the persistence layer.
 * </p>
 */
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    /**
     * Constructs a new {@code NoteController} with the provided {@link NoteService}.
     *
     * @param noteService the service used to manage notes
     */
    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Creates a new note.
     * <p>
     * This endpoint handles POST requests to create a new {@link Note}. The note data is provided
     * in the request body, and the created note is returned in the response with a status of 201 (Created).
     * </p>
     *
     * @param note the note to create
     * @return a {@link ResponseEntity} containing the created note and an HTTP status of 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note createdNote = noteService.createNote(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    /**
     * Updates an existing note.
     * <p>
     * This endpoint handles PUT requests to update an existing {@link Note}. The note ID is provided
     * in the path, and the updated note data is provided in the request body. The updated note is
     * returned in the response with a status of 200 (OK).
     * </p>
     *
     * @param id          the ID of the note to update
     * @param noteDetails the new details of the note
     * @return a {@link ResponseEntity} containing the updated note and an HTTP status of 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note noteDetails) {
        Note updatedNote = noteService.updateNote(id, noteDetails);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    /**
     * Deletes a note by its ID.
     * <p>
     * This endpoint handles DELETE requests to remove a {@link Note} by its ID. If the deletion
     * is successful, the response contains a status of 204 (No Content).
     * </p>
     *
     * @param id the ID of the note to delete
     * @return a {@link ResponseEntity} with an HTTP status of 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves all notes.
     * <p>
     * This endpoint handles GET requests to retrieve a list of all {@link Note} entities.
     * The notes are returned in the response with a status of 200 (OK).
     * </p>
     *
     * @return a {@link ResponseEntity} containing a list of notes and an HTTP status of 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    /**
     * Retrieves a note by its ID.
     * <p>
     * This endpoint handles GET requests to retrieve a specific {@link Note} by its ID.
     * The note is returned in the response with a status of 200 (OK).
     * </p>
     *
     * @param id the ID of the note to retrieve
     * @return a {@link ResponseEntity} containing the requested note and an HTTP status of 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    /**
     * Searches for notes by title.
     * <p>
     * This endpoint handles GET requests to search for {@link Note} entities based on their title.
     * The search term is provided as a request parameter, and the matching notes are returned in the
     * response with a status of 200 (OK).
     * </p>
     *
     * @param title the title to search for
     * @return a {@link ResponseEntity} containing a list of notes that match the search term and an HTTP status of 200 (OK)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Note>> searchNotesByTitle(@RequestParam String title) {
        List<Note> notes = noteService.searchNotesByTitle(title);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
}