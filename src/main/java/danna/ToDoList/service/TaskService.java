package danna.ToDoList.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import danna.ToDoList.dto.TaskResponseDetailsDto;
import danna.ToDoList.repository.TaskRespository;

@Service
public class TaskService {
    private final TaskRespository taskRespository;
    
    public TaskService(TaskRespository taskRespository){
        this.taskRespository = taskRespository;
    }

    public ResponseEntity<TaskResponseDetailsDto> getTasksDetailsById(Long taskId){
        // Obtengo el entity del task con el repository
        return taskRespository.findById(taskId)
        // se devuelve el dto
        .map(task -> ResponseEntity.ok(new TaskResponseDetailsDto(task)))
        .orElse(ResponseEntity.notFound().build());
    }
}
