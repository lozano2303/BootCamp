package bootCamp.Backend.model.ids;


import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Player_GameID implements Serializable{

    private int playerID;
    private int gameID;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.playerID;
        hash = 29 * hash + this.gameID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player_GameID other = (Player_GameID) obj;
        if (this.playerID != other.playerID) {
            return false;
        }
        return this.gameID == other.gameID;
    }

    
}
