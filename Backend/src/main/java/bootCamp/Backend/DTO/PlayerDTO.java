package bootCamp.Backend.DTO;

public class PlayerDTO {

    private String playerName;

    public PlayerDTO(){}

    public PlayerDTO(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
