package com.gurtoc.todos;

import com.gurtoc.todos.model.Task;
import com.gurtoc.todos.model.TaskGroup;
import com.gurtoc.todos.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository repository;

    public Warmup(TaskGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        LOGGER.info("App warmup after context refreshed");
        final String description = "AppContextEvent";
        if (!repository.existsByDescription(description)) {
            LOGGER.info("No eqq grp fonud! Adding it");
            var group = new TaskGroup();
            group.setDescription(description);
            group.setTaskSet(Set.of(
                    new Task("ContextCloseEvent", null, group),
                    new Task("ContextRefreshedEvent", null, group),
                    new Task("ContextStopvent", null, group),
                    new Task("ContextStartdEvent", null, group)
                    )
            );
            repository.save(group);
        }
    }
}
