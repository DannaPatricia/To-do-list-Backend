package danna.ToDoList.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import danna.ToDoList.dto.UserDto;
import danna.ToDoList.dto.UserRegisterDto;
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
    public ResponseEntity<UserRegisterDto> createUser(@RequestBody UserRegisterDto newUserDto) {
        return userService.createUser(newUserDto);
    }

    // Metodo para iniciar sesion, pilla por parametro el nombre de usuario y la contraseña, usando el dto
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }
    
    @GetMapping("/searchUser")
    public ResponseEntity<List<UserDto>> searchUserByUsername(@RequestParam String namePart) {
        List<UserDto> results = userService.searchUserByUsername(namePart);
        if(results.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(results);
    }
    
    
}
