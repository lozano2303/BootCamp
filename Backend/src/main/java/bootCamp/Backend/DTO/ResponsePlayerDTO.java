package bootCamp.Backend.DTO;

public class ResponsePlayerDTO {
    private String status;
    private String message;
    private PlayerDTO data;

    public ResponsePlayerDTO() {}

    public ResponsePlayerDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponsePlayerDTO(String status, String message, PlayerDTO data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters y Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public PlayerDTO getData() { return data; }
    public void setData(PlayerDTO data) { this.data = data; }
}
