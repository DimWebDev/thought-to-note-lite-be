package com.thoughttonotelite;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughttonotelite.model.Note;
import com.thoughttonotelite.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    private final NoteRepository noteRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public DataInitializer(NoteRepository noteRepository, ObjectMapper objectMapper) {
        this.noteRepository = noteRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {
        if (noteRepository.count() == 0) {
            // Load the JSON file
            ClassPathResource resource = new ClassPathResource("data/notes-data.json");
            InputStream inputStream = resource.getInputStream();

            // Convert JSON data to list of Note objects
            List<Note> notes = objectMapper.readValue(inputStream, new TypeReference<List<Note>>() {});

            // Save notes to the database
            noteRepository.saveAll(notes);
            System.out.println("Sample notes data loaded into the database.");
        } else {
            System.out.println("Database already contains data. Skipping data initialization.");
        }
    }
}