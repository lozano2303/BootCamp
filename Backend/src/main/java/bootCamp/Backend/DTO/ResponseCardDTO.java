package bootCamp.Backend.DTO;

import org.springframework.http.HttpStatus;

public class ResponseCardDTO<T> {
    private String status;
    private String message;
    private T cardData;  // Tipo genérico para datos de carta(s)

    // Constructores
    public ResponseCardDTO(HttpStatus status, String message, T cardData) {
        this.status = status.toString();
        this.message = message;
        this.cardData = cardData;
    }

    public ResponseCardDTO(HttpStatus status, String message) {
        this(status, message, null);
    }

    // Getters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getCardData() {
        return cardData;
    }

    // Setters (opcionales)
    public void setStatus(HttpStatus status) {
        this.status = status.toString();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCardData(T cardData) {
        this.cardData = cardData;
    }
}