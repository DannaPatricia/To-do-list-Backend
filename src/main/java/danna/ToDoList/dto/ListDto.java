package danna.ToDoList.dto;

public class ListDto {
    private Long id;
    private String name;
    private String userName;
    // de momento esto luego meto de que mande la list de dto de task
    
    public ListDto() {}
    
    // Constructor con todos los campos
    public ListDto(Long id, String name, String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}