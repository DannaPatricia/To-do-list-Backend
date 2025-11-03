package danna.ToDoList.dto.TagDto;

public class CreateTagDto {
    private String name;

    public CreateTagDto() {}

    public CreateTagDto(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
}