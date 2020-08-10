package com.gurtoc.todos.controller;

import com.gurtoc.todos.model.Task;
import com.gurtoc.todos.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Async//konieczna do wlaczenia asynch -> właczyć w jakiejś klasie konfiguracyjnej, aby działało
    public CompletableFuture<List<Task>> findAllTasks(){
        LOGGER.info("Async");
        return CompletableFuture.supplyAsync(taskRepository::findAll);
    }
}
