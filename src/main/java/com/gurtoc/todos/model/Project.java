package com.gurtoc.todos.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Projects desc must not be empty")
    private String description;
    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> taskGroupSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectStep> steps;

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

    public Set<TaskGroup> getTaskGroupSet() {
        return taskGroupSet;
    }

    public void setTaskGroupSet(Set<TaskGroup> taskGroupSet) {
        this.taskGroupSet = taskGroupSet;
    }
}
