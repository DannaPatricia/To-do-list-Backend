package danna.ToDoList.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

    // Se encarga de autenticar ususarios y devuelve un autenfication valido si el login fue correcto
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Postman o APIs públicas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/user/login", "/api/user/register")
                .permitAll() // estas rutas se pueden usar sin sesión
                .anyRequest().authenticated() // todas las demás requieren sesión
            );
            // .formLogin(form -> form
            //     .loginProcessingUrl("/api/user/login")
            //     .successHandler((request, response, authentication) -> {
            //         response.setContentType("application/json");
            //         response.getWriter().write("{\"message\":\"Login correcto\"}");
            //     })
            //     .failureHandler((request, response, exception) -> {
            //         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //         response.setContentType("application/json");
            //         response.getWriter().write("{\"error\":\"Credenciales incorrectas\"}");
            //     })
            // )
            // .sessionManagement(session -> session
            //     .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            // );

        return http.build();
    }
}
