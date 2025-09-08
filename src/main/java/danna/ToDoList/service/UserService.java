package danna.ToDoList.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import danna.ToDoList.dto.UserLoginDto;
import danna.ToDoList.dto.UserRequestDto;
import danna.ToDoList.model.UserEntity;
import danna.ToDoList.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Inyeccion por constructor de dependencias, en lugar de crar una instancia
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Response entity es un objeto que representa toda la respuesta http
    // DTO -> data transfer object -> para separar la estructura de datos del cliente, 
    // evitando exponer la lógica interna o la estructura completa de las entidades de la base de datos.
    public ResponseEntity<UserRequestDto> createUser(UserRequestDto newUserDto){
        // Se crea un userEntity a partir del userDto
        UserEntity userEntity = new UserEntity(newUserDto);
        if(!userRepository.existsByEmail(userEntity.getEmail()) && !userRepository.existsByUsername(userEntity.getUsername())){
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

            UserEntity userSaved = userRepository.save(userEntity);
            return ResponseEntity.ok(new UserRequestDto(userSaved));
            // Response entity devuelve un status y un body
            // status -> ok(body) -> 200
            // body -> userRepository.save(newUser)
            // devuelve codigo 200, ok

            // Otra manera de hacerlo
            // return ResponseEntity.ok(new UserRequestDto(userRepository.save(userEntity)));
        }
        // Caso en que el usuario ya existe, devuelve conflicto
        // con el .build() se devuelve la respuesta sin cuerpo
        return ResponseEntity.status(409).build();
    }

    public ResponseEntity<UserLoginDto> loginUser(UserLoginDto userDto){
        return userRepository.findByUsername(userDto.getUsername())
        // Se filtra por el usuario cuya contraseña coincida
        .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
        // se obtiene el mismo user y se transforma la entidad en dto como respuesta
        .map(user -> ResponseEntity.ok(new UserLoginDto(user)))
        // si no existe el user o la contraseña no coincide se manda un body vacio
        .orElse(ResponseEntity.status(401).build());
    }
}
