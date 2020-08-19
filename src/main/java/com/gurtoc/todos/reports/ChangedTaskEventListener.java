package com.gurtoc.todos.reports;

import com.gurtoc.todos.model.event.TaskDone;
import com.gurtoc.todos.model.event.TaskEvent;
import com.gurtoc.todos.model.event.TaskUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ChangedTaskEventListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    private final PersistedTaskEventRepository repository;

    public ChangedTaskEventListener(PersistedTaskEventRepository repository) {
        this.repository = repository;

    }

    @Async
    @EventListener
    public void on(TaskDone event) {
        onChanged(event);
    }

    @Async
    @EventListener
    public void on(TaskUndone event) {
        onChanged(event);
    }

    private void onChanged(final TaskEvent event) {
        LOGGER.info("Got " + event);
        repository.save(new PersistedTaskEvent(event));
    }
}
