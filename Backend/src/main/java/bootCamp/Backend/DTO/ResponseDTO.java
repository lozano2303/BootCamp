package bootCamp.Backend.DTO;

import java.util.List;

public class ResponseDTO {
    private String status;
    private String message;
    private List<PlayerGameDTO> data; // Campo específico para PlayerGameDTO

    // Constructor vacío
    public ResponseDTO() {}

    // Constructor para respuestas sin datos
    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Constructor para respuestas con datos de PlayerGameDTO
    public ResponseDTO(String status, String message, List<PlayerGameDTO> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters y Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public List<PlayerGameDTO> getData() { return data; }
    public void setData(List<PlayerGameDTO> data) { this.data = data; }
}