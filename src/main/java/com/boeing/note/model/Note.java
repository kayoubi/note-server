package com.boeing.note.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class Note {
    private String id;
    @NotEmpty
    @Length(min = 5, max = 255)
    private String title;

    @Length(max = 1024)
    private String content;

    public Note() {
    }

    public Note(String id, @NotEmpty @Length(min = 5, max = 255) String title, @Length(max = 1024) String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }



    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
