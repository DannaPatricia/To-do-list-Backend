package danna.ToDoList.dto;

import danna.ToDoList.model.UserEntity;
import jakarta.validation.constraints.NotBlank;

public class UserDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // Constructores
    public UserDto(){}
    public UserDto(UserEntity entity){
        this.username = entity.getUsername();
    }

    // Getters y Setters
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
