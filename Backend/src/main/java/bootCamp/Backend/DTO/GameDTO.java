package bootCamp.Backend.DTO;

import java.sql.Time;

public class GameDTO {

    private int GameID;
    private int cuantityPlayers;
    private Time gameTime;

    public GameDTO(int GameID, int cuantityPlayers, Time gameTime) {
        this.GameID = GameID;
        this.cuantityPlayers = cuantityPlayers;
        this.gameTime = gameTime;
    }

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int GameID) {
        this.GameID = GameID;
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

    
}
