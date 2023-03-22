package com.example.tasktracker.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaskState {
    public TaskState(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    Project project;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "createdAt")
    private Instant createdAt = Instant.now();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "task_state_id")
    @JsonBackReference
    private List<Task> tasksList = new ArrayList<>();

    public void addTask(Task task){
        this.tasksList.add(task);
    }


}
