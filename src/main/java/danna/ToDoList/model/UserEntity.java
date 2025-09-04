package danna.ToDoList.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="users")
// Esto le dice a JPA se encargará de asignar automáticamente la fecha y hora a campos como createdAt en las entidades.
@EntityListeners(AuditingEntityListener.class) // escucha para createdAt/updatedAt

public class UserEntity {
    @Id // Maraca este campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor se incrementara automaticamente por registro
    // y strategy = GenerationType.IDENTITY -> dice que l abase de datos se encarga de generar ese valor con el autoincrement
    private Long id;

    @NotBlank // No puede estar vacio
    @Column(unique=true) //Nombre unico
    private String userName;

    @NotBlank
    @Column(unique=true)
    private String email;

    @NotBlank
    private String passwordHash;
    private String role;

    @CreatedDate // Marca que este campo se llena automáticamente cuando se crea la entidad.
    // Se hace uso de esta anotacion ya que el EntityListener solo escucha aquellas columnas llamadas lit createdAt
    // Sin embargo, en la base de datos se llama created_at, por tanto se mapea el atributo con el nombre de la columna
    @Column(name="created_at", updatable=false) // Evita que se modifique al actualizar el usuario; mapea con la columna real de la base de datos.
    private LocalDateTime createdAt;

    // RELACIONES FORANEAS
    // Cardinalida 1:N un usuario puede tener muchas tareas
    @OneToMany(mappedBy="user") // mappedBy -> Indica donde esta la relacion en la otra entidad, o sea es el nombre del atributo user en TaskEntity
    private List<TaskEntity> tasks; // Para que desde el usuario se pueda acceder a su lista de task

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return userName; }
    public void setUsername(String userName) { this.userName = userName; }

    public String getPassword() { return passwordHash; }
    public void setPassword(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<TaskEntity> getTasks() { return tasks; }
    public void setTasks(List<TaskEntity> tasks) { this.tasks = tasks; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
