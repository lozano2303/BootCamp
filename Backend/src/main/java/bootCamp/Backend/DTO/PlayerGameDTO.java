package bootCamp.Backend.DTO;

import java.util.Objects;

public class PlayerGameDTO {
    private final int playerID;
    private final int gameID;
    private int score;

    // Constructor principal
    public PlayerGameDTO(int gameID, int playerID, int score) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.score = score;
    }

    // Getters
    public int getPlayerID() {
        return playerID;
    }

    public int getGameID() {
        return gameID;
    }

    public int getScore() {
        return score;
    }

    // Setter solo para score (si es necesario modificarlo)
    public void setScore(int score) {
        this.score = score;
    }

    // equals y hashCode para comparaciones
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerGameDTO that = (PlayerGameDTO) o;
        return playerID == that.playerID && 
               gameID == that.gameID && 
               score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, gameID, score);
    }

    // toString para logging/debugging
    @Override
    public String toString() {
        return "PlayerGameDTO{" +
               "playerID=" + playerID +
               ", gameID=" + gameID +
               ", score=" + score +
               '}';
    }
}