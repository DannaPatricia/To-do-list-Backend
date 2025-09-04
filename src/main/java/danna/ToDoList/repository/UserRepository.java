package danna.ToDoList.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import danna.ToDoList.model.UserEntity;

// ALGUNOS METODOS HEREDADOS:
// userRepository.save(new User()); → crear o actualizar
// userRepository.findById(1L); → buscar por ID
// userRepository.findAll(); -> listar todos
// userRepository.deleteById(2L); → borrar por ID
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    // ESTRUCTURA DE ESTOS METODOS: Tipo de valor a devolver nombre(tipo nombre)

    // Spring Data genera la consulta automáticamente usando JPQL o Criteria API.
    // Optional<T> -> es una clase contenedora que representa un valor que puede estar presente o no para evitar NullException
    // En lugar de usar if-else para verificar nulls y lanzar excepciones
    // Usar esta clase permite acceder a sus metodos de clase como el orElse o ifPresent 
    // para proporcionar un valor por defecto o ejecutar código solo si el valor está presente. 

    // query para obtener un usuario por su nombre de usuario
    // Optional<User> -> una "cajita" que puede contener un objeto User o estar vacia
    // query:findBy.. -> where ...
    Optional<UserEntity> findByUsername(String username);

    // Metodo para encontrar coincidencias por email de usuario
    boolean existsByEmail(String email);

    // Metodo para encontrar coincidencias por username de usuario
    boolean existsByUsername(String username);
}
