package com.boeing.note.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class Note {
    private String id;
    @NotEmpty
    @Length(min = 5, max = 255)
    private String title;

    @NotEmpty
    @Length(min = 5, max = 1024)
    private String summery;

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

    public String getSummery() {
        return summery;
    }

    public void setSummery(final String summery) {
        this.summery = summery;
    }
}
