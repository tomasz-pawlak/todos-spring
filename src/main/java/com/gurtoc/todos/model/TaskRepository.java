package com.gurtoc.todos.model;

//klasa sluzaca do polaczenia api do dzialania na zbiorze

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

//Jpa, klucz task i integer jako id, relacyjne
//Jpa tłumaczy metody na zapytania sql
@RepositoryRestResource
public
interface TaskRepository extends JpaRepository<Task,Integer>  {

    @Override
    @RestResource(exported = false)//zabezpieczenie przed kasowaniem
    void deleteById(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(Task task);

    //jpa podpowiada mozliwe funkcje
    //restres podmienia link
    @RestResource( path = "done", rel = "done")
    List<Task> findByDone(@Param("state") boolean done);
}
