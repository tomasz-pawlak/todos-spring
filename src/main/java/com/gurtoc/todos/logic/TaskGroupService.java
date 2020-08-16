package com.gurtoc.todos.logic;

import com.gurtoc.todos.TaskConfigurationProperties;
import com.gurtoc.todos.model.Project;
import com.gurtoc.todos.model.TaskGroup;
import com.gurtoc.todos.model.TaskGroupRepository;
import com.gurtoc.todos.model.TaskRepository;
import com.gurtoc.todos.model.projection.GroupReadModel;
import com.gurtoc.todos.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;
//    private TaskConfigurationProperties configurationProperties;

    public TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
//                            TaskConfigurationProperties configurationProperties) {
        this.repository = repository;
        this.taskRepository = taskRepository;
//        this.configurationProperties = configurationProperties;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        return createGroup(source, null);
    }

    public GroupReadModel createGroup(final GroupWriteModel source, final Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Grp has undone tasks. Done all tasks first");
        }
        TaskGroup result = repository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Taskgrp with id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }


}
