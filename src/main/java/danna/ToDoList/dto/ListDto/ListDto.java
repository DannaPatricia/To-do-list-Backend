package danna.ToDoList.dto.ListDto;

import danna.ToDoList.model.ListEntity;

public class ListDto {
    private String name;

    public ListDto() {}

    public ListDto(ListEntity listEntity) {
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
