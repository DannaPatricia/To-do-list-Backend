package danna.ToDoList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// Activa la auditoria
@EnableJpaAuditing // Esto le perimite a java rellenar automaticamente la fecha (no se creo que es temporar porque en teoria ya lo hace postgres)
public class ToDoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

}
