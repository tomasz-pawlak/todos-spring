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
import java.util.concurrent.CompletableFuture;

//@RepositoryRestController
@RestController //zwraca od razu ResponseBody i jest Component
@RequestMapping("/tasks") //mapowanie zapytania, aby nie było value przy metodach
public class TaskController {

    public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    TaskController(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

//    TaskController(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//    }

//    @GetMapping(params = {"!sort", "!page", "!size"})
//        //pod spodem ma RequestMapping z parametrem get - przechwytuje http.get i zwraca jsona
//    ResponseEntity<List<Task>> readAllTasks() {
//        LOGGER.warn("Show all tasks");
//        return ResponseEntity.ok(taskRepository.findAll());
//    }

    //wersja z ansych
    @GetMapping(params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks() {
        LOGGER.warn("Show all tasks");
        return taskService.findAllTasks().thenApply(ResponseEntity::ok);
    }

    @GetMapping
        //pod spodem ma RequestMapping z parametrem get - przechwytuje http.get i zwraca jsona
    ResponseEntity<List<Task>> readAllTasks(Pageable pageable) {
        LOGGER.warn("Show all tasks");
        return ResponseEntity.ok(taskRepository.findAll(pageable).getContent());
    }

    @PutMapping("/{id}")
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

    @GetMapping("/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    ResponseEntity<Task> createTask(@RequestBody @Valid Task saveTask) {
        Task result = taskRepository.save(saveTask);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @Transactional //dodanie aspektu
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
                .ifPresent(taskRepository -> taskRepository.setDone(!taskRepository.isDone()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state) {//@RequestParam - odwołanie parametru
        return ResponseEntity.ok(
                taskRepository.findByDone(state)
        );
    }

    @GetMapping("/search/id")
    ResponseEntity<?> findById(int id) {
        return ResponseEntity.ok(
                taskRepository.findById(id)
        );
    }

}
