package com.example.tasktracker.factories;


import com.example.tasktracker.dto.ProjectDto;
import com.example.tasktracker.models.Project;


import org.springframework.stereotype.Component;


@Component("ProjectDtoFactory")
public class ProjectDtoFactory {


    public ProjectDto getProjectDto(Project project){
        return new ProjectDto(project.
                getId(),project.
                getName(),project.
                getCreatedAt(),project.
                getTaskStates());
    }

}
