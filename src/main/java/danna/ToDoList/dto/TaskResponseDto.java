package danna.ToDoList.dto;

public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;

    public TaskResponseDto() {}
    
    // Constructor con los campos que se usara en el ListService
    public TaskResponseDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}