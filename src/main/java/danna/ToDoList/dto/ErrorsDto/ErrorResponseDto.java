package danna.ToDoList.dto.ErrorsDto;

// usar para manejar los errores de registro de usuario o luego mas cosas
public class ErrorResponseDto {
    private String succes;
    private String username;
    private String email;
    private String password;

    // Constructores, getters y setters
    public ErrorResponseDto() {}

    public ErrorResponseDto(String succes,String username, String email, String password) {
        this.succes = succes;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getSucces() { return succes; }

    public void setSucces(String succes) { this.succes = succes; }
}
