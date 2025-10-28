package danna.ToDoList.dto.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import danna.ToDoList.dto.TagDto.TagByUserDto;
import danna.ToDoList.model.TaskEntity;
import danna.ToDoList.model.TaskStatus;

public class TaskResponseDetailsDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private List<TagByUserDto> tags;
    private LocalDateTime updatedAt;
    private LocalDate dueDate;
    private String userName;

    // Constructores
    public TaskResponseDetailsDto() {
    }

    // Constructor con los campos que se usara en el TaskService y el ListService
    public TaskResponseDetailsDto(TaskEntity taskEntity) {
        this.id = taskEntity.getId();
        this.title = taskEntity.getTitle();
        this.description = taskEntity.getDescription();
        this.status = taskEntity.getStatus();

        // Si hay tags null solo crea un array list o si no caput
        this.tags = taskEntity.getTags() != null
                ? taskEntity.getTags().stream()
                        .map(TagByUserDto::new)
                        .toList()
                : new ArrayList<>();

        this.updatedAt = taskEntity.getUpdatedAt();
        this.dueDate = taskEntity.getDueDate();
        this.userName = taskEntity.getUser().getUsername();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<TagByUserDto> getTags() {
        return tags;
    }

    public void setTags(List<TagByUserDto> tags) {
        this.tags = tags;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
