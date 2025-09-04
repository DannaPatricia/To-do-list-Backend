package danna.ToDoList.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="Task")

// Esto le dice a JPA se encargará de asignar automáticamente la fecha y hora a campos como createdAt en las entidades.
@EntityListeners(AuditingEntityListener.class) // escucha para createdAt/updatedAt
public class TaskEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank // Es de jakarta, se encarga de validar que no este vacio, solo sean espacios en blanco o sea null a nivel javaç
    @Column(nullable=false)
    private String title;
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Valida a nivel de BD que no sea NULL
    private TaskStatus status;

    @NotNull
    @Column(name = "due_date")
    private LocalDate dueDate;

    @NotNull // -> Ya que notBlank solo apolica para strings
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id") // ->  indica que la columna en la base de datos para una asociación de entidades
    // o una colección de elementos debe llamarse "user_id"
    private UserEntity user;
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
