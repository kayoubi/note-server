package com.boeing.note.controller;

import com.boeing.note.excption.NoteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalController {
    @ExceptionHandler({NoteNotFoundException.class})
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public void notFound() {
        System.out.println("not found");
    }
}
