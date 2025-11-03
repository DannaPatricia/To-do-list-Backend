package danna.ToDoList.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danna.ToDoList.dto.Task.TaskRequestDto;
import danna.ToDoList.dto.Task.TaskResponseDetailsDto;
import danna.ToDoList.dto.Task.TaskUpdateDto;
import danna.ToDoList.security.CustomUserDetails;
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

    // Para obtener todas las tareas de una lista
    @GetMapping("/list/{listId}")
    public ResponseEntity<List<TaskResponseDetailsDto>> getTasksByListId(@PathVariable Long listId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return taskService.getTasksByListId(listId, customUserDetails.getId());
    }

    // Crear una tarea mediante el id de la lista
    @PostMapping("/create/{listId}")
    public ResponseEntity<TaskResponseDetailsDto> createTask(@RequestBody TaskRequestDto taskDto, @PathVariable Long listId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (taskDto == null || taskDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return taskService.createTask(taskDto, listId, customUserDetails.getId());
    }
    
    // Modificar la tarea mediante su id
    @PutMapping("/update/{taskId}")
    public ResponseEntity<TaskResponseDetailsDto> updateTask(@RequestBody TaskUpdateDto taskDto,
        @PathVariable Long taskId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
            return taskService.updateTask(taskDto, taskId, customUserDetails.getId());
    }

    // Eliminar una tarea
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long taskId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (taskId == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return taskService.deleteTask(taskId, customUserDetails.getId());
    }
}
