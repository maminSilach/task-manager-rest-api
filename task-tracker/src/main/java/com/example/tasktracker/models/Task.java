package com.example.tasktracker.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    public Task(String name,String description) {
        this.name = name;
        this.description = description;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "createdAt")
    private Instant createdAt = Instant.now();

    @Column(name = "description")
    private String description;

    @ManyToOne
    private TaskState taskState;


}
