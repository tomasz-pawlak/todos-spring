package com.gurtoc.todos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = IllegalExceptionProcessing.class)//pozwala na obsluge bledow globalnie, każdy controller bedzie mial te obsluge bledow
public class IllegalExceptionControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument (IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<?> handleIllegalState (IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
