package com.example.tasktracker.dto;

import com.example.tasktracker.models.TaskState;

import lombok.*;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Instant createdAt;
    @NonNull
    private List<TaskState> taskStates;

}
