package bootCamp.Backend.model;

import java.io.Serializable;

import bootCamp.Backend.model.ids.Player_GameID;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity(name="player_game")
public class PlayerGame implements Serializable{

    @EmbeddedId
    private Player_GameID player_GameID;


    @ManyToOne
    @MapsId("playerID")
    @JoinColumn(name="playerID", nullable=false)
    private Player playerID; 

    @ManyToOne
    @MapsId("gameID")
    @JoinColumn(name="gameID", nullable=false)
    private Game gameID; 

    @Column(name="score")
    private int score;

    @Column(name="position")
    private int position;

    public PlayerGame(){}

    public PlayerGame(Player_GameID player_GameID, Player playerID, Game gameID, int score, int position) {
        this.player_GameID = player_GameID;
        this.playerID = playerID;
        this.gameID = gameID;
        this.score = score;
        this.position = position;
    }

    public Player_GameID getPlayer_GameID() {
        return player_GameID;
    }

    public void setPlayer_GameID(Player_GameID player_GameID) {
        this.player_GameID = player_GameID;
    }

    public Player getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Player playerID) {
        this.playerID = playerID;
    }

    public Game getGameID() {
        return gameID;
    }

    public void setGameID(Game gameID) {
        this.gameID = gameID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    

    
}
