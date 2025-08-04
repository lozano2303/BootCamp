package bootCamp.Backend.DTO;

import java.util.List;

public class GameResponseDTO {
    private String status;
    private String message;
    private List<GameDTO> data;

    public GameResponseDTO() {}

    public GameResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public GameResponseDTO(String status, String message, List<GameDTO> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters y setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GameDTO> getData() {
        return data;
    }

    public void setData(List<GameDTO> data) {
        this.data = data;
    }
}
