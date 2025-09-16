package danna.ToDoList.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import danna.ToDoList.model.UserEntity;
import danna.ToDoList.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
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

        // Crea una clase CustomUserDetails que implementa UserDetails, le paso el UserEntity por constructor
        return new CustomUserDetails(userEntity);
    }
}
