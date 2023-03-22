package com.example.tasktracker.dto;

import com.example.tasktracker.models.Project;
import com.example.tasktracker.models.Task;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStateDto {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Instant createdAt = Instant.now();


    @NonNull
    private List<Task> tasksList;
}
