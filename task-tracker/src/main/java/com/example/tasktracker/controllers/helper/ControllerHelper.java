package com.example.tasktracker.controllers.helper;


import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.models.Project;
import com.example.tasktracker.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
@Transactional
public class ControllerHelper {

    private final ProjectRepository projectRepository;


    public Project getProjectOrElseThrow(Long id){
        return projectRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("project with " + id + " not found");
        });
    }
}
