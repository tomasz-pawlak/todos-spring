package com.gurtoc.todos.controller;

import com.gurtoc.todos.model.Task;
import com.gurtoc.todos.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

//@RepositoryRestController
@RestController //zwraca od razu ResponseBody i jest Component
public class TaskController {

    public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
        //pod spodem ma RequestMapping z parametrem get - przechwytuje http.get i zwraca jsona
    ResponseEntity<List<Task>> readAllTasks() {
        LOGGER.warn("Show all tasks");
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @GetMapping("/tasks")
        //pod spodem ma RequestMapping z parametrem get - przechwytuje http.get i zwraca jsona
    ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {
        LOGGER.warn("Show all tasks");
        return ResponseEntity.ok(taskRepository.findAll(pageable).getContent());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task toUpdate) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    taskRepository.save(task);
                });
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build()
                );
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task saveTask) {
        Task result = taskRepository.save(saveTask);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @Transactional //dodanie aspektu
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
                .ifPresent(taskRepository -> taskRepository.setDone(!taskRepository.isDone()));
        return ResponseEntity.noContent().build();
    }

}
