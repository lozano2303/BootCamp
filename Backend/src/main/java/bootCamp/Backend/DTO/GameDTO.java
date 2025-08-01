package bootCamp.Backend.DTO;

import java.sql.Time;

public class GameDTO {

    private int cuantityPlayers;
    private Time gameTime;

    public GameDTO(int cuantityPlayers, Time gameTime) {
        this.cuantityPlayers = cuantityPlayers;
        this.gameTime = gameTime;
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
