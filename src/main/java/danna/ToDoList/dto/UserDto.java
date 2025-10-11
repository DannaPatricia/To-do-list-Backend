package danna.ToDoList.dto;

import danna.ToDoList.model.UserEntity;
import jakarta.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // Para el frontend
    private Long id;

    // Constructores
    public UserDto(){}
    public UserDto(UserEntity entity){
        this.username = entity.getUsername();
        this.id = entity.getId();
    }

    // Getters y Setters
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
}
