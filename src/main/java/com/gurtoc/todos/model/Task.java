package com.gurtoc.todos.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity //JPA - ze bedzie tabela z klasy
@Table(name = "tasks")
public class Task {

    @Id //dla hibernate
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(name = "desc") //mapowanie nazwy kolumny - mozna prez settera też
    @NotBlank(message = "Task desc must not be null")
    private String description;
    private boolean done;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
