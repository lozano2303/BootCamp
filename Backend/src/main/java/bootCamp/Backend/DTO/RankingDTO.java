package bootCamp.Backend.DTO;

public class RankingDTO {
    private int playerID;
    private int bestScore;
    private int totalWins;

    public RankingDTO(int bestScore, int playerID, int totalWins) {
        this.bestScore = bestScore;
        this.playerID = playerID;
        this.totalWins = totalWins;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }



}
