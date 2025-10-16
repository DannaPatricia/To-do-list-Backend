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
import danna.ToDoList.dto.ErrorsDto.ErrorResponseDto;
import danna.ToDoList.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/user")
// para permitir Angular
// Controlador, hara uso del service se usuarios para controlar qu elogica implementar dependiendo de la llamada
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // Meotdo para registar un usuario
    @PostMapping("/register")
    public ResponseEntity<ErrorResponseDto> createUser(@RequestBody UserRegisterDto newUserDto) {
        return userService.createUser(newUserDto);
    }

    // Metodo para iniciar sesion, pilla por parametro el nombre de usuario y la contraseña, usando el dto
    // HttpServletRequest -> Representa la peticion http, asi se podra forzar la creacion de la cookie al poder aceder a la peticion
    // Se leyo previamente que la mejor pratica es usar tokens, pero debido a su complejidad se decidio no usarlo
    // Como es el primer proyecto fullstack con SpringBoot y angular se decidio no realizar codigos mas complejos y asegurar el aprendizaje
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userDto, HttpServletRequest request) {
        return userService.loginUser(userDto, request);
    }
    
    @GetMapping("/searchUser")
    public ResponseEntity<List<UserDto>> searchUserByUsername(@RequestParam String namePart) {
        List<UserDto> results = userService.searchUserByUsername(namePart);
        if(results.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(results);
    }

    // Metodo para verificar si hay una sesion activa
    @GetMapping("/check-session")
    public ResponseEntity<UserDto> checkSession(HttpServletRequest request) {
        return userService.checkSession(request);
    }

    // Desloguear al usuario
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        return userService.logout(request);
    }
}
