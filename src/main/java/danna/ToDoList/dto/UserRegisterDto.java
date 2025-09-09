package danna.ToDoList.dto;

import danna.ToDoList.model.UserEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class UserRegisterDto {
    @NotBlank // No puede estar vacio
    @Column(unique=true) //Nombre unico
    private String username;

    @NotBlank
    @Column(unique=true)
    private String email;

    @NotBlank
    @Column(name="password_hash")
    private String password;


    // Constructores
    public UserRegisterDto(){}

    // Crea un dto a partir de un entity
    public UserRegisterDto(UserEntity entity) {
        this.password = entity.getPassword();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
    }

    // Getters y setters
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
