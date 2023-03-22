package com.example.tasktracker.controllers;



import com.example.tasktracker.dto.TaskDto;
import com.example.tasktracker.exception.BadRequestException;
import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.factories.TaskDtoFactory;
import com.example.tasktracker.models.Task;
import com.example.tasktracker.models.TaskState;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.TaskStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@Transactional
@RequiredArgsConstructor
public class TaskController {
    private final TaskStateRepository taskStateRepository;
    private final TaskRepository taskRepository;
    private final TaskDtoFactory taskDtoFactory;

    public static final String CREATE_TASK = "/api/project/{task_states_id}/task";
    public static final String DELETE_TASK = "/api/project/task/{task_id}";
    public static final String EDIT_TASK = "/api/project/task/{task_id}";
    public static final String FETCH_TASK = "/api/project/task";




    @PostMapping(CREATE_TASK)
    public TaskDto createTask(
            @PathVariable("task_states_id") Long id,
            @RequestParam("name") String nameTask,
            @RequestBody(required = false) String descriptionTask) {
        isEmpty(nameTask);
        Optional<String> optionalDescriptionTask  = Optional.ofNullable(descriptionTask);
        TaskState taskState = taskStateRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Task states not exists");
        });
        taskRepository.findByNameAndTaskStateId(nameTask,taskState.getId()).ifPresent((task) -> {
            throw new BadRequestException("Task with name " + nameTask + " already exists");
        });
        Task task = new Task(nameTask, optionalDescriptionTask.orElse("None"));
        task.setTaskState(taskState);
        taskState.addTask(task);
        taskRepository.saveAndFlush(task);
        return taskDtoFactory.getTaskDto(task);
    }

    @GetMapping(FETCH_TASK)
    public List<TaskDto> fetchTask(@RequestParam(value = "id",required = false) Long id){
        Optional<Long> optionalTaskName = Optional.ofNullable(id);
        Stream<Task> taskStream = taskRepository.streamAllBy();
        if(optionalTaskName.isPresent()){
          Task task = taskRepository.findById(id).orElseThrow(() -> {
              throw new BadRequestException("Task with id " + id + " not found");
          });
          return taskRepository.findById(id).map(taskDtoFactory::getTaskDto).stream().toList();
        }
        return taskStream.map(taskDtoFactory::getTaskDto).toList();
    }


    @PatchMapping(EDIT_TASK)
    public TaskDto editTask(@PathVariable("task_id") Long id,
                            @RequestParam("name") String taskName,
                            @RequestBody(required = false) String description){
        Optional<String> optionalTaskDescription = Optional.ofNullable(description);
        Task task = getTaskOrThrows(id);
        isEmpty(taskName);
        task.setName(taskName);
        task.setDescription(optionalTaskDescription.orElse(task.getDescription()));
        return taskDtoFactory.getTaskDto(task);

    }


    @DeleteMapping(DELETE_TASK)
    public Boolean deleteTask(@PathVariable("task_id") Long id){
        Task task = getTaskOrThrows(id);
        taskRepository.delete(task);
        return true;
    }


    private Task getTaskOrThrows(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Task not exists");
        });
    }

    private static void isEmpty(String taskName) {
        if(taskName.isBlank()){
            throw new BadRequestException("Name can't be is empty");
        }
    }

}
