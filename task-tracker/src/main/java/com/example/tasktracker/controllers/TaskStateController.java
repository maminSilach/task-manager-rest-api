package com.example.tasktracker.controllers;



import com.example.tasktracker.controllers.helper.ControllerHelper;
import com.example.tasktracker.dto.TaskStateDto;
import com.example.tasktracker.exception.BadRequestException;
import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.factories.TaskStateDtoFactory;
import com.example.tasktracker.models.Project;
import com.example.tasktracker.models.TaskState;
import com.example.tasktracker.repositories.TaskStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Stream;


@RestController
@RequiredArgsConstructor
@Transactional
public class TaskStateController {
    private final TaskStateDtoFactory taskStateDtoFactory;
    private final TaskStateRepository taskStateRepository;
    private final ControllerHelper controllerHelper;

    public static final String DELETE_TASK_STATES = "/api/project/task-states/{task_states_id}";
    public static final String EDIT_TASK_STATES = "/api/project/task-states/{task_states_id}";
    public static final String CREATE_TASK_STATES = "/api/project/{project_id}/task-states";
    public static final String GET_TASK_STATES = "/api/project/{project_id}/task-states";




    @GetMapping(GET_TASK_STATES)
    public List<TaskStateDto> getTaskStates(@PathVariable("project_id") Long projectId){
         Project project = controllerHelper.getProjectOrElseThrow(projectId);
         Stream<TaskState> taskStateStream = taskStateRepository.streamAllByProjectId(project.getId());
         return taskStateStream.map(taskStateDtoFactory::getTaskStateDto).toList();
}


    @PostMapping(CREATE_TASK_STATES)
    public TaskStateDto addTaskStates(
            @PathVariable("project_id") Long projectId,
            @RequestParam(name = "name") String nameTaskStates){
        findByName(nameTaskStates);
        isEmpty(nameTaskStates);
        Project project = controllerHelper.getProjectOrElseThrow(projectId);
        TaskState taskState = new TaskState(nameTaskStates);
        project.addTaskStates(taskState);
        taskStateRepository.saveAndFlush(taskState);
        return taskStateDtoFactory.getTaskStateDto(taskState);
    }


    @PatchMapping(EDIT_TASK_STATES)
    public TaskStateDto editTaskStateDto(@PathVariable("task_states_id") Long taskStatesId,
                                         @RequestParam("name") String taskStatesName){
        findByName(taskStatesName);
        isEmpty(taskStatesName);
        TaskState taskState = getTaskStateOrElseThrow(taskStatesId);
        taskState.setName(taskStatesName);
        return taskStateDtoFactory.getTaskStateDto(taskState);
    }

    private void findByName(String taskStatesName) {
        taskStateRepository.findByName(taskStatesName).ifPresent((project -> {
            throw new BadRequestException("project already exists");
        }));
    }


    @DeleteMapping(DELETE_TASK_STATES)
    public Boolean deleteTaskStateDto(@PathVariable("task_states_id") Long taskStatesId){
        TaskState taskState = getTaskStateOrElseThrow(taskStatesId);
        taskStateRepository.delete(taskState);
        return true;
    }

    private TaskState getTaskStateOrElseThrow(Long taskStatesId) {
        return taskStateRepository.findById(taskStatesId).orElseThrow(() -> {
            throw new NotFoundException("Task state with " + taskStatesId + " not found");
        });
    }


    private void isEmpty(String taskStatesName) {
        if(taskStatesName.trim().isBlank()){
            throw new BadRequestException("Name can't be empty");
        }
    }

}
