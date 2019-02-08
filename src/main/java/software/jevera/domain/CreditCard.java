package main.java.software.jevera.domain;

import java.time.Instant;

public class CreditCard {

    private User owner;
    private Integer cardNumberHash;
    private Instant deathDate;
    private Short cvv;
    private Integer cash;

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getCardNumberHash() {
        return cardNumberHash;
    }

    public void setCardNumberHash(Integer cardNumberHash) {
        this.cardNumberHash = cardNumberHash;
    }

    public Instant getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Instant deathDate) {
        this.deathDate = deathDate;
    }

    public Short getCvv() {
        return cvv;
    }

    public void setCvv(Short cvv) {
        this.cvv = cvv;
    }
}
