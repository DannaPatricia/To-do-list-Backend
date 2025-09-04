package danna.ToDoList.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // @Bean -> Esto le dice a visual devuelve un objeto que quiero que Spring gestione como un bean
    // Un bean es básicamente un objeto que Spring crea, inicializa y reutiliza donde se necesite
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Postman o APIs públicas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/user/login", "/api/user/register", "/api/user").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
