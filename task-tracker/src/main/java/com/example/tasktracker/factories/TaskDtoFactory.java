package com.example.tasktracker.factories;


import com.example.tasktracker.dto.TaskDto;
import com.example.tasktracker.models.Task;
import org.springframework.stereotype.Component;


@Component("TaskDtoFactory")
public class TaskDtoFactory {


    public TaskDto getTaskDto(Task task){
        return new TaskDto(task.
                getId(),task.
                getName(),task.
                getCreatedAt(),task.
                getDescription(),task.
                getTaskState());
    }

}
