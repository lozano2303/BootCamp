package bootCamp.Backend.DTO;

public class CardDTO {

    private String img;
    private String cardName;
    private Short stoke;
    private Short defense;
    private Short average;
    private Short goals;
    private Short pass;
    private Short price;

    public CardDTO(Short average, String cardName, Short defense, Short goals, String img, Short pass, Short price, Short stoke) {
        this.average = average;
        this.cardName = cardName;
        this.defense = defense;
        this.goals = goals;
        this.img = img;
        this.pass = pass;
        this.price = price;
        this.stoke = stoke;
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

    public Short getStoke() {
        return stoke;
    }

    public void setStoke(Short stoke) {
        this.stoke = stoke;
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
