package danna.ToDoList.dto.ListDto;

import danna.ToDoList.model.ListEntity;

public class CreateListDto {
    private String name;

    public CreateListDto() {}

    public CreateListDto(ListEntity listEntity) {
        this.name = listEntity.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmpty() {
        return name == null || name.trim().isEmpty();
    }
}
