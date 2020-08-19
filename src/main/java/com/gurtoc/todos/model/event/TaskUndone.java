package com.gurtoc.todos.model.event;

import com.gurtoc.todos.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent{
    TaskUndone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
