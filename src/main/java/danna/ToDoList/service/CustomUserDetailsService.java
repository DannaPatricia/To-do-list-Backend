package danna.ToDoList.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import danna.ToDoList.model.UserEntity;
import danna.ToDoList.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    // Contenido de userDetailsService -> 
    // public interface UserDetailsService {
    //     UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    // }

    private final UserRepository userRepository;

    public CustomUserDetailsService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    // Convierte el UserEntity en un UserDetails que Spring Security pueda usar
    // Contiene username, contraseña hasheada y roles (authorities)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // Busca el usuario en la base de datos y lo convierte en un UserDetails que Spring Security pueda usar para autenticar.
        UserEntity userEntity = userRepository.findByUsername(username)
        // si no encuenta el usuario lanza una excepcion
        .orElseThrow(()->new UsernameNotFoundException("User not found"));

        // Devuelve un userDetail con los datos modificados, los datos del user que se logea
        return org.springframework.security.core.userdetails.User.builder()
        .username(userEntity.getUsername())
        .password(userEntity.getPassword()) // contraseña ya hasheada
        .roles("USER") // roles fijos por ahora
        .build();
    }
}
