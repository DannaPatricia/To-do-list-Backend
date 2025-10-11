package danna.ToDoList.dto.ListDto;

import danna.ToDoList.model.ListEntity;

public class ListDto {
    private String name;
    private Long id;
    private String userName;

    public ListDto() {}

    public ListDto(ListEntity listEntity) {
        this.name = listEntity.getName();
        this.id = listEntity.getId();
        this.userName = listEntity.getUser().getUsername();
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmpty() {
        return name == null || name.trim().isEmpty();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
