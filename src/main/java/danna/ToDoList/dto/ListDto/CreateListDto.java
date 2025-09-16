package danna.ToDoList.dto.ListDto;

import danna.ToDoList.model.ListEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateListDto {

    @NotBlank(message = "no debe estar vacío")
    private String name;

    @NotNull(message = "no debe estar nulo")
    private Long userId;

    // Constructor vacío (necesario para JSON)
    public CreateListDto() {}

    // Constructor desde la entidad (para devolverlo en la respuesta)
    public CreateListDto(ListEntity entity) {
        this.name = entity.getName();
        this.userId = entity.getUser().getId();
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Validación básica
    public boolean isEmpty() {
        return (this.name == null || this.name.isBlank() || this.userId == null);
    }
}
