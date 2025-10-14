package danna.ToDoList.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.List;

import danna.ToDoList.security.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // @Bean -> Esto le dice a visual devuelve un objeto que quiero que Spring
    // gestione como un bean
    // Un bean es básicamente un objeto que Spring crea, inicializa y reutiliza
    // donde se necesite
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Se encarga de autenticar ususarios y devuelve un autenfication valido si el
    // login fue correcto
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Postman o APIs públicas
                .cors(cors -> {}) // cors del bean de abajo para el fromtend
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/login", "/api/user/register", "/api/user/logout")
                        .permitAll() // estas rutas se pueden usar sin sesión
                        .anyRequest().authenticated() // todas las demás requieren sesión
                )
                // Se señala la clase que utiliza la interfaz UserDetailsService para el Spring
                // Security
                .userDetailsService(customUserDetailsService)
                // se crea obligatoriamente la sesion s es necesario
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        return http.build();
    }
}


