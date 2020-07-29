package com.gurtoc.todos.logic;

import com.gurtoc.todos.model.Task;
import com.gurtoc.todos.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempService {
    @Autowired
    List<String> temp(TaskGroupRepository repository){
        //FIXME: N+1
        return repository.findAll().stream()
                .flatMap(taskGroup -> taskGroup.getTaskSet().stream())
                .map(Task::getDescription)
                .collect(Collectors.toList());
    }
}
