package bootCamp.Backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="card")
public class Card {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="cardID", nullable=false)
    private int cardID;

    @Column(name="img", length=256, nullable=false)
    private String img;

    @Column(name="cardName", length=20, nullable=false)
    private String cardName;

    @Column(name="stroke", nullable=false)
    private Short stroke;

    @Column(name="defense", nullable=false)
    private Short defense;

    @Column(name="average", nullable=false)
    private Short average;

    @Column(name="goals", nullable=false)
    private Short goals;

    @Column(name="pass", nullable=false)
    private Short pass;

    @Column(name="price", nullable=false)
    private Short price;

    public Card(){}

    public Card(int cardID, String img, String cardName, Short stroke, Short defense, Short average, Short goals,
            Short pass, Short price) {
        this.cardID = cardID;
        this.img = img;
        this.cardName = cardName;
        this.stroke = stroke;
        this.defense = defense;
        this.average = average;
        this.goals = goals;
        this.pass = pass;
        this.price = price;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Short getStroke() {
        return stroke;
    }

    public void setStroke(Short stroke) {
        this.stroke = stroke;
    }

    public Short getDefense() {
        return defense;
    }

    public void setDefense(Short defense) {
        this.defense = defense;
    }

    public Short getAverage() {
        return average;
    }

    public void setAverage(Short average) {
        this.average = average;
    }

    public Short getGoals() {
        return goals;
    }

    public void setGoals(Short goals) {
        this.goals = goals;
    }

    public Short getPass() {
        return pass;
    }

    public void setPass(Short pass) {
        this.pass = pass;
    }

    public Short getPrice() {
        return price;
    }

    public void setPrice(Short price) {
        this.price = price;
    }

    

}
