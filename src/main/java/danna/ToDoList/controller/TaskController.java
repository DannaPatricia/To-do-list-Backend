package danna.ToDoList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danna.ToDoList.dto.TaskResponseDetailsDto;
import danna.ToDoList.service.TaskService;


@RestController
@RequestMapping("api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    // Diferencia entre requestParam y PathVariable
    // RequestParam -> Se suele yusar para filtrados o busquedas, ya que no es parte de la ruta principal
    // PathVariable -> Se suele usar para una busqueda especifica, es parte de la ruta principal
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDetailsDto> getTasksDetailsById(@PathVariable Long taskId) {
        return taskService.getTasksDetailsById(taskId);
    }
    
}
