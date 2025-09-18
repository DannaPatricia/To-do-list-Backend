package danna.ToDoList.dto.ListDto;

// Este dto lo uso para tanto crear y eliminar el compartir una lista
public class ShareListDto {
    private Long listId;
    private Long userId; // el usuario con el que se va a compartir

    // getters y setters
    public Long getListId() { return listId; }
    
    public void setListId(Long listId) { this.listId = listId; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public boolean isEmpty() {
        return listId == null || userId == null;
    }
}
