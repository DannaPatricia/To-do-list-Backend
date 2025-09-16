package danna.ToDoList.dto;

import java.time.LocalDateTime;

import danna.ToDoList.model.TaskEntity;
import danna.ToDoList.model.TaskStatus;

public class TaskResponseDetailsDto {
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime updatedAt;

    // Constructores
    public TaskResponseDetailsDto() {}
    
    // Constructor con los campos que se usara en el TaskService y el ListService
    public TaskResponseDetailsDto(TaskEntity taskEntity) {
        this.title = taskEntity.getTitle();
        this.description = taskEntity.getDescription();
        this.status = taskEntity.getStatus();
        this.updatedAt = taskEntity.getUpdatedAt();
    }
    
    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() {return status;}
    public void setStatus(TaskStatus status) {this.status = status;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
