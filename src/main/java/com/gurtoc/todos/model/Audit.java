package com.gurtoc.todos.model;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

//@MappedSuperclass//klasa bazowa, ktora nie ma swojego odpowiednika w tabeli
@Embeddable // mozna ja osadzic w innej klasie, aby niełatwić się w milionowych dziedziczeniach
public class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;


    @PrePersist
//funkcja ktora sie odpali tuz przed dodaniem encji do bazy danych
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preMerge() {
        updatedOn = LocalDateTime.now();
    }
}
