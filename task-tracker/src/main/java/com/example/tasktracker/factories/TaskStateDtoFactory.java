package com.example.tasktracker.factories;



import com.example.tasktracker.dto.TaskStateDto;
import com.example.tasktracker.models.TaskState;
import org.springframework.stereotype.Component;


@Component("TaskStateDtoFactory")
public class TaskStateDtoFactory {


    public TaskStateDto getTaskStateDto(TaskState taskState){
        return new TaskStateDto(taskState.
                getId(),taskState.
                getName(), taskState.
                getCreatedAt(),taskState.
                getTasksList());
    }

}
