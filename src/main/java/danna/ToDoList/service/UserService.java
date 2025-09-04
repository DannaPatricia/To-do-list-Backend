package danna.ToDoList.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        return ResponseEntity.status(409).build();
    }
}
