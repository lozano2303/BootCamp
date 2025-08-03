package bootCamp.Backend.DTO;

public class PlayerGameDTO {
    private int playerID;
    private int gameID;
    private int score;

    public PlayerGameDTO(int gameID, int playerID, int score) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.score = score;
    }

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
