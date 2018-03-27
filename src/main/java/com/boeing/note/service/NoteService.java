package com.boeing.note.service;

import com.boeing.note.excption.NoteNotFoundException;
import com.boeing.note.model.Note;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) // needs to be SINGLETON to maintain one List
public class NoteService {
    // since this is a shared instance
    private List<Note> notes = Collections.synchronizedList(new ArrayList<>());

    // IDs are thread-safe
    private final AtomicInteger ids = new AtomicInteger();

    public List<Note> getNotes() {
        return notes;
    }

    public Note getNote(final String id) {
        return notes.stream().filter(n -> n.getId().equals(id)).findAny().orElseThrow(NoteNotFoundException::new);
    }

    public Note createNote(Note note) {
        final String id = Integer.toString(ids.getAndIncrement());
        note.setId(id);
        notes.add(0, note);

        return note;
    }
}
