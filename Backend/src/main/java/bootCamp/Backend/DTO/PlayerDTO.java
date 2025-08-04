package bootCamp.Backend.DTO;

public class PlayerDTO {

    private Integer playerID;  // <-- agregamos playerID
    private String playerName;

    public PlayerDTO(){}

    public PlayerDTO(Integer playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Integer playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
