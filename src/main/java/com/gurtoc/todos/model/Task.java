package com.gurtoc.todos.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity //JPA - ze bedzie tabela z klasy
@Table(name = "tasks")
public class Task extends BaseAuditableEntity{

    @Id //dla hibernate
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(name = "desc") //mapowanie nazwy kolumny - mozna prez settera też
    @NotBlank(message = "Task desc must not be null")
    private String description;
    private boolean done;
//    @Column - dodanie wlasnej nazwy kolumny itp.
    private LocalDateTime deadline;

    //cammelCase w java, w sql przestawiany na "created_on"
    //@Transient - nie bedzie pokazywac w kolumnie
    //ukrywamy przed tym co jest w JSON, ale leci do db
//    private LocalDateTime createdOn;
//    private LocalDateTime updatedOn;

    public Task() {
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void updateFrom(final Task source){
        description = source.description;
        done = source.done;
        deadline = source.deadline;
    }


}
