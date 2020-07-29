package com.gurtoc.todos.adapter;

import com.gurtoc.todos.model.Project;
import com.gurtoc.todos.model.ProjectRepository;
import com.gurtoc.todos.model.TaskGroup;
import com.gurtoc.todos.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("select distinct  g from Project g join fetch g.steps") //hql
    List<Project> findAll();


}
