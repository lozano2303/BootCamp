package bootCamp.Backend.model;

import java.io.Serializable;
import bootCamp.Backend.model.ids.Player_GameID;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "player_game") // Nombre explícito de la tabla
public class PlayerGame implements Serializable {

    @EmbeddedId
    private Player_GameID id; // Cambiado a nombre más simple

    @ManyToOne
    @MapsId("playerID") // Debe coincidir exactamente con el campo en Player_GameID
    @JoinColumn(name = "playerID", referencedColumnName = "playerID", nullable = false)
    private Player player;

    @ManyToOne
    @MapsId("gameID") // Debe coincidir exactamente con el campo en Player_GameID
    @JoinColumn(name = "gameID", referencedColumnName = "gameID", nullable = false)
    private Game game;

    @Column(name = "score", nullable = false)
    private int score;

    // Constructores
    public PlayerGame() {
    }

    public PlayerGame(Player_GameID id, Player player, Game game, int score) {
        this.id = id;
        this.player = player;
        this.game = game;
        this.score = score;
    }

    // Getters y setters
    public Player_GameID getId() {
        return id;
    }

    public void setId(Player_GameID id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Métodos útiles
    public int getPlayerId() {
        return this.id.getPlayerID();
    }

    public int getGameId() {
        return this.id.getGameID();
    }
}