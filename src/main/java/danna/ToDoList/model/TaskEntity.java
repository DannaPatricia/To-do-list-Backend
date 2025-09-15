package danna.ToDoList.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import danna.ToDoList.dto.TaskResponseDetailsDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="tasks")

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
    
    @Enumerated(EnumType.STRING) // Esto le permite a la BD que guarde una de las constantes declaradas en el ENUM, STRING indica que guardara el nombre, no de la posicion de la costante como con ORDINAL
    @Column(nullable = false) // Valida a nivel de BD que no sea NULL
    private TaskStatus status;

    @NotNull
    @Column(name = "due_date")
    private LocalDate dueDate;

    @NotNull // -> Ya que notBlank solo apolica para strings
    @CreatedDate // Recordamos para que pille automanticamente la fecha actual cuando la entidad se crea
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Esta se rellena automáticamente cada vez que la entidad se actualiza.
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // RELACIONES FORANEAS: ESTOS CAMPOS ESTAN DECLARADAS COMO CLAVES FORANEAS EN ESTA ENTIDAD ;)
    @ManyToOne // Cardinalidad N:1
    @JoinColumn(name = "user_id") // ->  indica que este atributo hace referencia a la columna user_id
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="list_id") // ->  indica que este atributo hace referencia a la columna list_id
    private ListEntity list;

    @ManyToMany // Relacion N:M
    @JoinTable( //Indica que esta relacion se resuelve con una tabla intermedia
        name="task_tags", // Nombre de la tabla intermedia
        joinColumns= @JoinColumn(name="task_id"), // Indica CUAL es la columna, de esa tabla intermedia, que apunta a la entidad actual
        inverseJoinColumns= @JoinColumn(name="tag_id") // Indica CUAL es la columna, de esa tabla intermedia, que apunta a la entidad de la otra tabla
    )
    private List<TagEntity> tags; // Nombre del atributo -> tags

    // Constructor
    public TaskEntity(){}

    // Constructor para usar en el TaskService
    public TaskEntity(TaskResponseDetailsDto taskResponseDetailsDto){
        this.title = taskResponseDetailsDto.getTitle();
        this.description = taskResponseDetailsDto.getDescription();
        this.status = taskResponseDetailsDto.getStatus();
        this.updatedAt = taskResponseDetailsDto.getUpdatedAt();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public TaskStatus getStatus() {return status;}
    public void setStatus(TaskStatus status) {this.status = status;}

    public LocalDate getDueDate() {return dueDate;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

    public UserEntity getUser() {return user;}
    public void setUser(UserEntity user) {this.user = user;}

    public ListEntity getList() {return list;}
    public void setList(ListEntity list) {this.list = list;}

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }
}
