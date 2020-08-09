package com.gurtoc.todos.logic;

import com.gurtoc.todos.TaskConfigurationProperties;
import com.gurtoc.todos.model.*;
import com.gurtoc.todos.model.projection.GroupReadModel;
import com.gurtoc.todos.model.projection.GroupTaskWriteModel;
import com.gurtoc.todos.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties configurationProperties;
    private TaskGroupService service;

    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository, TaskConfigurationProperties configurationProperties, TaskGroupService service) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.configurationProperties = configurationProperties;
        this.service = service;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project save(Project toSave) {
        return projectRepository.save(toSave);
    }

//    public GroupReadModel createGroup(LocalDateTime deadline, int projectID) {
//        if (!configurationProperties.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectID)) {
//            throw new IllegalStateException("Only one grp from project is allowed");
//        }
//        TaskGroup taskGroup = projectRepository.findById(projectID)
//                .map(project -> {
//                    var result = new TaskGroup();
//                    result.setDescription(project.getDescription());
//                    result.setTaskSet(project.getSteps().stream()
//                            .map(projectStep ->
//                                    new Task(projectStep.getDescription(), deadline.plusDays(projectStep.getDaysToDeadline())))
//                            .collect(Collectors.toSet())
//                    );
//                    result.setProject(project);
//                    return taskGroupRepository.save(result);
//                }).orElseThrow(()->new IllegalArgumentException("Project with given id not found") );
//        return new GroupReadModel(taskGroup);
//    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectID) {
        if (!configurationProperties.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectID)) {
            throw new IllegalStateException("Only one grp from project is allowed");
        }
        return projectRepository.findById(projectID)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                .map(projectStep -> {
                                    var task = new GroupTaskWriteModel();
                                    task.setDescription(projectStep.getDescription());
                                    task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                    return task;
                                }
                                ).collect(Collectors.toSet())
                    );
                    return service.createGroup(targetGroup);
                }).orElseThrow(()->new IllegalArgumentException("Project with given id not found"));
    }
}
