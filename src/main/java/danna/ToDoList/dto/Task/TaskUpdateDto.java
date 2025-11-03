package danna.ToDoList.dto.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import danna.ToDoList.model.TagEntity;
import danna.ToDoList.model.TaskEntity;
import danna.ToDoList.model.TaskStatus;

public class TaskUpdateDto {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;

    // Constructores
    public TaskUpdateDto() {}
    
    // Constructor con los campos que se usara en el TaskService y el ListService
    public TaskUpdateDto(TaskEntity taskEntity) {
        this.title = taskEntity.getTitle();
        this.description = taskEntity.getDescription();
        this.status = taskEntity.getStatus();
        this.dueDate = taskEntity.getDueDate();
    }

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() {return status;}
    public void setStatus(TaskStatus status) {this.status = status;}

    public LocalDate getDueDate() {return dueDate;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    // Metodos de validacion
    public boolean isEmpty() {
        return title == null || title.trim().isEmpty();
    }
}
