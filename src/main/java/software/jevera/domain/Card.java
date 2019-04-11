package software.jevera.domain;

import lombok.*;


import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "cardNumber")
@ToString
public class Card {

    private User owner;
    private String cardNumber;
    private String cvv;
    private Instant endDate;

    public Card(String cardNumber, String cvv, Instant endDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.endDate = endDate;
    }

    public Card(){

    }
//
//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }
//
//    public String getCardNumber() {
//        return cardNumber;
//    }
//
//    public void setCardNumber(String cardNumber) {
//        this.cardNumber = cardNumber;
//    }
//
//    public String getCvv() {
//        return cvv;
//    }
//
//    public void setCvv(String cvv) {
//        this.cvv = cvv;
//    }
//
//    public Instant getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(Instant endDate) {
//        this.endDate = endDate;
//    }
}
