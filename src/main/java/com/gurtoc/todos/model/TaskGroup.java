package com.gurtoc.todos.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity //JPA - ze bedzie tabela z klasy
@Table(name = "task_groups")
public class TaskGroup {

    @Id //dla hibernate
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(name = "desc") //mapowanie nazwy kolumny - mozna prez settera też
    @NotBlank(message = "Task group desc must not be null")
    private String description;
    private boolean done;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")//jeden taskgroup idzie do wielu taskow, ale nie uzywac List - w hibernate nie zachowuje kolejnosci
    private Set<Task> taskSet;
    @ManyToOne //wiele taskow moze trafic do jednej grupy
    @JoinColumn(name = "project_id") //po tej kolumnie dołączamy te dane
    private Project project;


    //cammelCase w java, w sql przestawiany na "created_on"
    //@Transient - nie bedzie pokazywac w kolumnie
    //ukrywamy przed tym co jest w JSON, ale leci do db
//    private LocalDateTime createdOn;
//    private LocalDateTime updatedOn;

    public TaskGroup() {
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

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(final Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
