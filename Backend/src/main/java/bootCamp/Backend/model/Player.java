package bootCamp.Backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity (name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="playerID")
    private int playerID;

    @Column(name="playerName", length=15, nullable=false)
    private String playerName;

    public Player () {}

    public Player(int playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
