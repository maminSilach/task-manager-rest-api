package com.example.tasktracker.repositories;




import com.example.tasktracker.models.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;


public interface TaskStateRepository extends JpaRepository<TaskState,Long> {
    Stream<TaskState> streamAllByProjectId(Long projectId);
    Optional<TaskState> findByName(String name);

}
