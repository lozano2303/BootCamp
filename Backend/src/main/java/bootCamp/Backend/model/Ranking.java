package bootCamp.Backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity(name="ranking")
public class Ranking {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="rankingID")
    private int rankingID;

    @ManyToOne
    @JoinColumn(name="playerID", nullable=false)
    private Player playerID;

    @Column(name="bestScore")
    private int bestScore;

    @Column(name="totalWins")
    private int totalWins;

    public Ranking(){}

    public Ranking(int rankingID, Player playerID, int bestScore, int totalWins) {
        this.rankingID = rankingID;
        this.playerID = playerID;
        this.bestScore = bestScore;
        this.totalWins = totalWins;
    }

    public int getRankingID() {
        return rankingID;
    }

    public void setRankingID(int rankingID) {
        this.rankingID = rankingID;
    }

    public Player getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Player playerID) {
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
