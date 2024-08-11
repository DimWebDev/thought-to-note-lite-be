package com.thoughttonotelite.service;

import com.thoughttonotelite.model.Note;
import com.thoughttonotelite.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Note} entities.
 * <p>
 * This service provides business logic and handles operations related to {@link Note} entities.
 * It interacts with the {@link NoteRepository} to perform CRUD operations and custom queries.
 * The service methods ensure that business rules are enforced before interacting with the database.
 * </p>
 */
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * Constructs a new {@code NoteService} with the provided {@link NoteRepository}.
     *
     * @param noteRepository the repository used to interact with the persistence layer
     */
    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Creates a new note.
     * <p>
     * This method saves the provided {@link Note} entity to the database using the {@link NoteRepository}.
     * </p>
     *
     * @param note the note to be created
     * @return the created note
     */
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Updates an existing note by its ID.
     * <p>
     * This method finds the note by its ID, updates its title and content with the provided details,
     * and then saves the updated note back to the database. If the note is not found, it throws a
     * {@link RuntimeException}.
     * </p>
     *
     * @param id          the ID of the note to update
     * @param noteDetails the new details of the note
     * @return the updated note
     * @throws RuntimeException if the note with the specified ID is not found
     */
    public Note updateNote(Long id, Note noteDetails) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            Note note = optionalNote.get();
            note.setTitle(noteDetails.getTitle());
            note.setContent(noteDetails.getContent());
            return noteRepository.save(note);
        } else {
            throw new RuntimeException("Note not found with id " + id);
        }
    }

    /**
     * Deletes a note by its ID.
     * <p>
     * This method checks if a note with the specified ID exists and, if so, deletes it from the database.
     * If the note is not found, it throws a {@link RuntimeException}.
     * </p>
     *
     * @param id the ID of the note to delete
     * @throws RuntimeException if the note with the specified ID is not found
     */
    public void deleteNoteById(Long id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Note not found with id " + id);
        }
    }

    /**
     * Retrieves all notes.
     * <p>
     * This method returns a list of all {@link Note} entities from the database.
     * </p>
     *
     * @return a list of all notes
     */
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    /**
     * Retrieves a note by its ID.
     * <p>
     * This method finds a {@link Note} by its ID. If the note is not found, it throws a
     * {@link RuntimeException}.
     * </p>
     *
     * @param id the ID of the note to retrieve
     * @return the note with the specified ID
     * @throws RuntimeException if the note with the specified ID is not found
     */
    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id " + id));
    }

    /**
     * Searches for notes by title.
     * <p>
     * This method returns a list of {@link Note} entities whose titles contain the specified keyword,
     * ignoring case.
     * </p>
     *
     * @param title the keyword to search for in the title
     * @return a list of notes that match the search criteria
     */
    public List<Note> searchNotesByTitle(String title) {
        return noteRepository.findByTitleContainingIgnoreCase(title);
    }
}