package com.example.tasktracker.repositories;



import com.example.tasktracker.models.Project;
import com.example.tasktracker.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;


public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findByNameAndTaskStateId(String name,Long id);
    Stream<Task> streamAllBy();
    Stream<Task> streamAllByNameIgnoreCase(String name);
    Optional<Task> findByName(String name);

}
