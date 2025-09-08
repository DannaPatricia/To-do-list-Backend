package danna.ToDoList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import danna.ToDoList.dto.UserLoginDto;
import danna.ToDoList.dto.UserRequestDto;
import danna.ToDoList.service.UserService;


@RestController
@RequestMapping("api/user")
// @CrossOrigin(origins = "http://localhost:4200") // para permitir Angular más adelante

// Controlador, hara uso del service se usuarios para controlar qu elogica implementar dependiendo de la llamada
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // Meotdo para registar un usuario
    @PostMapping("/register")
    public ResponseEntity<UserRequestDto> createUser(@RequestBody UserRequestDto newUserDto) {
        return userService.createUser(newUserDto);
    }

    // Metodo para iniciar sesion, pilla por parametro el nombre de usuario y la contraseña, usando el dto
    @PostMapping("/login")
    public ResponseEntity<UserLoginDto> loginUser(@RequestBody UserLoginDto userDto) {
        return userService.loginUser(userDto);
    }
    
    
}
