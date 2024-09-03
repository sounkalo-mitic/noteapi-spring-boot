package com.example.noteapi.controller;

import com.example.noteapi.models.Notes;
import com.example.noteapi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    // Créer une nouvelle note
    @PostMapping
    public Notes createNote(@RequestBody Notes note) {
        return noteRepository.save(note);
    }

    // Lire toutes les notes
    @GetMapping
    public List<Notes> getAllNotes() {
        return noteRepository.findAll();
    }

    // Lire une note par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Notes> getNoteById(@PathVariable Long id) {
        Notes note = noteRepository.findById(id).orElse(null);
        return ResponseEntity.ok(note);
    }

    // Mettre à jour une note
    @PutMapping("/{id}")
    public ResponseEntity<Notes> updateNote(@PathVariable Long id, @RequestBody Notes noteDetails) {
        Notes note = noteRepository.findById(id).orElse(null);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        Notes updatedNote = noteRepository.save(note);
        return ResponseEntity.ok(updatedNote);
    }

    // Supprimer une note
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        Notes note = noteRepository.findById(id).orElse(null);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        noteRepository.delete(note);
        return ResponseEntity.noContent().build();
    }
}
