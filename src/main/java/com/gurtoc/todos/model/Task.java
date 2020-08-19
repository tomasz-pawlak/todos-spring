package com.gurtoc.todos.model;

import com.gurtoc.todos.model.event.TaskEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
    //    @Column - dodanie wlasnej nazwy kolumny itp.
    private LocalDateTime deadline;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne //wiele taskow moze trafic do jednej grupy
    @JoinColumn(name = "task_group_id") //po tej kolumnie dołączamy te dane
    private TaskGroup group;

    //cammelCase w java, w sql przestawiany na "created_on"
    //@Transient - nie bedzie pokazywac w kolumnie
    //ukrywamy przed tym co jest w JSON, ale leci do db
//    private LocalDateTime createdOn;
//    private LocalDateTime updatedOn;

//    public Task() {
//    }

//    public Task(@NotBlank(message = "Task desc must not be null") String description, LocalDateTime deadline) {
//        this.description = description;
//        this.deadline = deadline;
//    }

    public Task(String description, LocalDateTime deadline) {
        this(description, deadline, null);
    }

    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.description = description;
        this.deadline = deadline;
        if (group != null) {
            this.group = group;
        }
    }

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

    //    public void setDone(boolean done) {
//        this.done = done;
//    }
    public TaskEvent toggle() {
        this.done = !this.done;
        return TaskEvent.changed(this);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TaskGroup getGroup() {
        return group;
    }

    public void setGroup(TaskGroup group) {
        this.group = group;
    }

    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        group = source.group;
    }


}
