package com.example.tasktracker.dto;

import com.example.tasktracker.models.TaskState;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;

    @NonNull
    private String name;

    @NonNull

    private Instant createdAt;

    @NonNull
    private String description;

    @NonNull
    private TaskState taskState;
}
