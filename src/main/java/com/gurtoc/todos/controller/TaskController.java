package com.gurtoc.todos.controller;

import com.gurtoc.todos.model.Task;
import com.gurtoc.todos.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RepositoryRestController
public class TaskController {

    public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"}) //pod spodem ma RequestMapping z parametrem get - przechwytuje http.get i zwraca jsona
    ResponseEntity<List<Task>> readAllTasks(){
        LOGGER.warn("Show all tasks");
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping("/tasks") //pod spodem ma RequestMapping z parametrem get - przechwytuje http.get i zwraca jsona
    ResponseEntity<List<Task>> readAllTasks(Pageable pageable){
        LOGGER.warn("Show all tasks");
        return ResponseEntity.ok(taskRepository.findAll(pageable).getContent());
    }
}
