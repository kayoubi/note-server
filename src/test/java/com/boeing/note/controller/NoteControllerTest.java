package com.boeing.note.controller;

import com.boeing.note.excption.NoteNotFoundException;
import com.boeing.note.model.Note;
import com.boeing.note.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
/**
 * @author khaled
 */
@WebMvcTest
@RunWith(SpringRunner.class)
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Test
    public void testGetNotesOk() throws Exception {
        when(noteService.getNotes()).thenReturn(
            Collections.singletonList(new Note("1", "note title", "note content"))
        );

        this.mockMvc.perform(MockMvcRequestBuilders.get("/notes"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("@[0].id").value("1"))
            .andExpect(MockMvcResultMatchers.jsonPath("@[0].title").value("note title"))
            .andExpect(MockMvcResultMatchers.jsonPath("@[0].content").value("note content"));
    }

    @Test
    public void testCreateNoteWithNoTitleReturn400() throws Exception {
        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/notes")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(new Note(null, null, "some content"))))
            .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testGetNonExistingNoteReturn404() throws Exception {
        when(noteService.getNote(anyString())).thenThrow(NoteNotFoundException.class);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/notes/2"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError())
            .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void testCreateNoteReturn201() throws Exception {
        final Note in = new Note(null, "foobar", "barsdfadadf");
        final Note out = new Note("1", in.getTitle(), in.getContent());
        when(noteService.createNote(any())).thenReturn(out);

        this.mockMvc
            .perform(MockMvcRequestBuilders.post("/notes")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(new ObjectMapper().writeValueAsString(in)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/notes/" + out.getId()))
            .andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(out)))
        ;
    }
}