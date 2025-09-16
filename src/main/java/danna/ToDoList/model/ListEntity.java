package danna.ToDoList.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import danna.ToDoList.dto.ListDto.CreateListDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="lists")
@EntityListeners(AuditingEntityListener.class) // escucha para createdAt/updatedAt
public class ListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @CreatedDate
    @Column(name="created_at", updatable=false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id") // ->  indica que este atributo hace referencia a la columna user_id
    private UserEntity user;

    @OneToMany(mappedBy="list")  // mappedBy -> Indica donde esta la relacion en la otra entidad, o sea es el nombre del atributo, list, en TaskEntity
    private List<TaskEntity> tasks; // De esta manera desde la lista se podra acceder a sus tasks

    @ManyToMany
    @JoinTable(
        name="list_shared", //Nombre de la tabla intermedia
        joinColumns= @JoinColumn(name="list_id"), // Esta tabla intermedia apunta al campo list_id que apunta a esta entidad
        inverseJoinColumns= @JoinColumn(name="user_id") // Esta tabla apunta al campo user_id que hace referencia a la otra entidad UsersEntity
    )
    private List<UserEntity> sharedWith; // Nombre del atributo -> sharedWith

    // Constructor para crear una lista
    public ListEntity() {}
    
    // Getter y Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public UserEntity getUser() {return user;}
    public void setUser(UserEntity user) {this.user = user;}

    public List<TaskEntity> getTasks() {return tasks;}
    public void setTasks(List<TaskEntity> tasks) {this.tasks = tasks;}

    public List<UserEntity> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(List<UserEntity> sharedWith) {
        this.sharedWith = sharedWith;
    }
}
