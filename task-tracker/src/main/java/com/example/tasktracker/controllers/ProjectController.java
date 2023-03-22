package com.example.tasktracker.controllers;




import com.example.tasktracker.controllers.helper.ControllerHelper;
import com.example.tasktracker.dto.ProjectDto;
import com.example.tasktracker.exception.BadRequestException;
import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.factories.ProjectDtoFactory;
import com.example.tasktracker.models.Project;
import com.example.tasktracker.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@Transactional
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectDtoFactory projectDtoFactory;
    private final ProjectRepository projectRepository;
    private final ControllerHelper controllerHelper;


    public static final String DELETE_PROJECT = "/api/project/{id}";
    public static final String EDIT_PROJECT = "/api/project/{id}";
    public static final String CREATE_PROJECT = "/api/project";
    public static final String FETCH_PROJECT = "/api/project";


    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name) {
        isEmpty(name);
        projectRepository.findByName(name).ifPresent((project -> {
            throw new BadRequestException("project already exists");
        }));
        Project project = projectRepository.saveAndFlush(new Project(name.toLowerCase()));
        return projectDtoFactory.getProjectDto(project);
    }




    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editProject(@PathVariable("id") Long id,
                                  @RequestParam String name) {
        isEmpty(name);
        Project project =  controllerHelper.getProjectOrElseThrow(id);
        findByName(name);
        project.setName(name);
    project = projectRepository.saveAndFlush(project);
    return projectDtoFactory.getProjectDto(project);
    }

    private void findByName(String name) {
        projectRepository.findByName(name).ifPresent((project1) -> {
            throw new BadRequestException("Project with name " + name + " already exists");
        });
    }

    @GetMapping(FETCH_PROJECT)
    public List<ProjectDto> fetchProject(@RequestParam(name = "name",required = false) String prefix_name){
        Optional<String> name = Optional.ofNullable(prefix_name);
        Stream<Project>  streamList = projectRepository.streamAllBy();
        List<ProjectDto> listDto = streamList.map(projectDtoFactory::getProjectDto).toList();
        if(name.isPresent()){
            projectRepository.findByName(name.get().trim().toLowerCase()).orElseThrow(() -> {
                throw new NotFoundException("project not found ");
            });
            return projectRepository.
                    streamAllByNameIgnoreCase(name.get()).map(projectDtoFactory::getProjectDto).
                    toList();
        }
        return listDto;
    }




    @DeleteMapping(DELETE_PROJECT)
    public Boolean deleteProject(@PathVariable("id") Long id){
        controllerHelper.getProjectOrElseThrow(id);
        projectRepository.deleteById(id);
        return true;
    }

    private static void isEmpty(String name) {
        if(name.trim().isEmpty()){
            throw new BadRequestException("name is empty");
        }
    }

}
