package bootCamp.Backend.DTO;

public class ResponseDTO {

    private String status;
    private String message;

    public ResponseDTO(){}

    public ResponseDTO(String message, String status) {
        this.message = message;
        this.status = status;
    }

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



}
