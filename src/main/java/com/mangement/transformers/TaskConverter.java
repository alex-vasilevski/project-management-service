package com.mangement.transformers;

import com.mangement.dto.Task;
import com.mangement.domain.TaskEntity;
import org.springframework.stereotype.Component;

@Component("taskConverter")
public class TaskConverter implements Converter<TaskEntity, Task> {
    @Override
    public Task toDto(TaskEntity taskEntity) {
        return null;
    }

    @Override
    public TaskEntity toEntity(Task task) {
        return null;
    }
}