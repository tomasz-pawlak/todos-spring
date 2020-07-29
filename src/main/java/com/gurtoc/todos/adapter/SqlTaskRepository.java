package com.gurtoc.todos.adapter;

//klasa sluzaca do polaczenia api do dzialania na zbiorze

import com.gurtoc.todos.model.Task;
import com.gurtoc.todos.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

//Jpa, klucz task i integer jako id, relacyjne
//Jpa tłumaczy metody na zapytania sql
//@RepositoryRestResource
@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task,Integer>  {

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

//    @Override
//    @RestResource(exported = false)//zabezpieczenie przed kasowaniem
//    void deleteById(Integer integer);
//
//    @Override
//    @RestResource(exported = false)
//    void delete(Task task);

    //jpa podpowiada mozliwe funkcje
    //restres podmienia link
//    @RestResource( path = "done", rel = "done")
//    List<Task> findByDone(@Param("state") boolean done);
}
