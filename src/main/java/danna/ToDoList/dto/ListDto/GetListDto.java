package danna.ToDoList.dto.ListDto;

import java.util.List;

import danna.ToDoList.dto.TaskResponseDetailsDto;
import danna.ToDoList.model.ListEntity;

public class GetListDto {
    private Long id;
    private String name;
    private String userName;
    private List<TaskResponseDetailsDto> listTasks;
    
    // Constructores
    public GetListDto() {}

    // Constructor para pasar de entity a dto
    public GetListDto(ListEntity listEntity) {
        this.id = listEntity.getId();
        this.name = listEntity.getName();
        this.userName = listEntity.getUser().getUsername();
        // Se obtiene la lista de tareas (en dto) mediante su constructor
        this.listTasks = listEntity.getTasks().stream().map(TaskResponseDetailsDto::new).toList();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public List<TaskResponseDetailsDto> getTaskDtos() { return listTasks; }
    public void setListTasks(List<TaskResponseDetailsDto> listTasks) { this.listTasks = listTasks; }
}