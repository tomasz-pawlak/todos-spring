package com.gurtoc.todos.logic;

import com.gurtoc.todos.TaskConfigurationProperties;
import com.gurtoc.todos.model.ProjectRepository;
import com.gurtoc.todos.model.TaskGroupRepository;
import com.gurtoc.todos.model.TaskRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    void createGrp_noMultiGrpConfig_And_undoneGrpExists_throwsIllegalStateE(){
        //given
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(false);

        var toTest = new ProjectService(null,mockGroupRepository,mockConfig);

        //when
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one grp");
    }

    @Test
    void createGrp_configOK_and_noProjects_throwIllegalArgumentException(){
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        //system under test
        var toTest = new ProjectService(mockRepository,null, mockConfig);

        //when
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    void createGrp_noMultiGrpConfig_and_noUndoweGrpExistats_noProject_throwsIllegalArgumentException(){
        //given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //and
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);

        var toTest = new ProjectService(mockRepository,mockGroupRepository,mockConfig);

        //when
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");

    }

    @Test

    private TaskGroupRepository groupRepositoryReturning(final boolean result){
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private TaskConfigurationProperties configurationReturning(final boolean result){
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }



}