package com.boeing.note.controller;

import com.boeing.note.model.Note;
import com.boeing.note.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    NoteController(final NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

    @GetMapping("/{id}")
    public Note getNote(final @PathVariable String id) {
        return noteService.getNote(id);
    }

    @PostMapping
    public ResponseEntity createNote(final @Valid @RequestBody Note note) {
        final String id = noteService.createNote(note);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }
}
