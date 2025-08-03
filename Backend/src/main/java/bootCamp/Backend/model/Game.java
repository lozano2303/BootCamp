package bootCamp.Backend.model;

import java.io.Serializable;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="game")
public class Game implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="gameID")
    private int gameID;

    @Column(name="cuantityPlayers", nullable=false)
    private int cuantityPlayers;

    @Column(name="gameTime", nullable=false)
    private Time gameTime;

    @Column(name="winner", length=20)
    private String winner;


    public Game(){}

    public Game(int gameID, int cuantityPlayers, Time gameTime, String winner) {
        this.gameID = gameID;
        this.cuantityPlayers = cuantityPlayers;
        this.gameTime = gameTime;
        this.winner = winner;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getCuantityPlayers() {
        return cuantityPlayers;
    }

    public void setCuantityPlayers(int cuantityPlayers) {
        this.cuantityPlayers = cuantityPlayers;
    }

    public Time getGameTime() {
        return gameTime;
    }

    public void setGameTime(Time gameTime) {
        this.gameTime = gameTime;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
