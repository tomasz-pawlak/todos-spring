//package com.gurtoc.todos.logic;
//
//
//import com.gurtoc.todos.TaskConfigurationProperties;
//import com.gurtoc.todos.model.ProjectRepository;
//import com.gurtoc.todos.model.TaskGroupRepository;
//import com.gurtoc.todos.model.TaskRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//class LogicConfiguration {
//    @Bean
//    ProjectService projectService(
//            final ProjectRepository repository,
//            final TaskGroupRepository taskGroupRepository,
//            final TaskGroupService taskGroupService,
//            final TaskConfigurationProperties config
//    ) {
//        return new ProjectService(repository, taskGroupRepository, config, taskGroupService);
//    }
//
//    @Bean
//    TaskGroupService taskGroupService(
//            final TaskGroupRepository taskGroupRepository,
//            final TaskRepository taskRepository
//    ) {
//        return new TaskGroupService(taskGroupRepository, taskRepository);
//    }
//}
