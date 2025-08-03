package bootCamp.Backend.model.ids;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Player_GameID implements Serializable {

    @Column(name = "playerID", nullable = false)
    private int playerID;

    @Column(name = "gameID", nullable = false)
    private int gameID;

    // Constructor por defecto requerido por JPA
    public Player_GameID() {}

    // Constructor conveniente
    public Player_GameID(int playerID, int gameID) {
        this.playerID = playerID;
        this.gameID = gameID;
    }

    // Getters y setters
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    // equals() optimizado
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Player_GameID that = (Player_GameID) o;
        return playerID == that.playerID && 
               gameID == that.gameID;
    }

    // hashCode() mejorado
    @Override
    public int hashCode() {
        return 31 * playerID + gameID;
    }

    // toString() útil para debugging
    @Override
    public String toString() {
        return "Player_GameID{" +
               "playerID=" + playerID +
               ", gameID=" + gameID +
               '}';
    }
}