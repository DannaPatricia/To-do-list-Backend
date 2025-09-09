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
    // userDetails representa un usuario logeable, no se puede hacer uso de un entity ya que spring security necesita
    // conocer el rol, username... del usuario, y eso se consigue con UserDetail
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // Se busca al usuario para obtener sus datos y devolver un userDetail con los datos de este
        // y poder guardar la sesion de user con la cookie en UserService
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
