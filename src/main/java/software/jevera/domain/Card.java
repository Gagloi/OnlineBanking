package main.java.software.jevera.domain;

import java.time.Instant;

public class Card {

    private User owner;
    private Integer cardNumber;
    private Short cvv;
    private Instant endDate;

    public Card(User owner, Integer cardNumber, Short cvv, Instant endDate) {
        this.owner = owner;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.endDate = endDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Short getCvv() {
        return cvv;
    }

    public void setCvv(Short cvv) {
        this.cvv = cvv;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
