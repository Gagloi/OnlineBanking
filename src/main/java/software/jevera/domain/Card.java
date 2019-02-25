package main.java.software.jevera.domain;

import java.time.Instant;

public class Card {

    private User owner;
    private String cardNumber;
    private String cvv;
    private Instant endDate;
    private Integer count;

    public Card(User owner, String cardNumber, String cvv, Instant endDate, Integer count) {
        this.owner = owner;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.endDate = endDate;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
