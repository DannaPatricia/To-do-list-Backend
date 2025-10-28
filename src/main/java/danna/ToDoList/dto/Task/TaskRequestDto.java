package danna.ToDoList.dto.Task;

import java.time.LocalDate;

public class TaskRequestDto {
    private String title;
    private String description;
    private LocalDate dueDate;
    
    public TaskRequestDto() {}
    
    // Constructor con los campos que se usara en el ListService
    public TaskRequestDto(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
    
    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() {return dueDate;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    // Metodos de validacion
    public boolean isEmpty() {
        return title == null || title.trim().isEmpty();
    }
}