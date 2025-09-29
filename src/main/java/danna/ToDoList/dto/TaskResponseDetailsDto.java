package danna.ToDoList.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import danna.ToDoList.model.TagEntity;
import danna.ToDoList.model.TaskEntity;
import danna.ToDoList.model.TaskStatus;

public class TaskResponseDetailsDto {
    private String title;
    private String description;
    private TaskStatus status;
    private List<String> tags;
    private LocalDateTime updatedAt;
    private LocalDate dueDate;
    private String userName;

    // Constructores
    public TaskResponseDetailsDto() {}
    
    // Constructor con los campos que se usara en el TaskService y el ListService
    public TaskResponseDetailsDto(TaskEntity taskEntity) {
        this.title = taskEntity.getTitle();
        this.description = taskEntity.getDescription();
        this.status = taskEntity.getStatus();
        // Se verifica si la lista de tags esta vacia, si es el caso se asigna una vacia, sino la pasada por la entidad
        // RECORDATORIO, EL .toList() CREA UAN LISTA INMUTABLE, NO SE PODRA MODIFICAR, POR TANTO LO MEJOR ES USARLO SOLO 
        // PARA DEVOLVERLO JUNTO AL RESPONSENTITY
        this.tags = (taskEntity.getTags() != null && !taskEntity.getTags().isEmpty())
            ? taskEntity.getTags().stream().map(TagEntity::getName).collect(Collectors.toList())
            : new ArrayList<>();
        this.updatedAt = taskEntity.getUpdatedAt();
        this.dueDate = taskEntity.getDueDate();
        this.userName = taskEntity.getUser().getUsername();
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

    public LocalDate getDueDate() {return dueDate;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    public List<String> getTags() {return tags;}
    public void setTags(List<String> tags) {this.tags = tags;}

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
