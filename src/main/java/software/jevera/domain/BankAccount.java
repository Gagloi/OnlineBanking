package software.jevera.domain;

import lombok.Getter;
import lombok.Setter;
import software.jevera.service.bankaccount.BankAccountState;
import software.jevera.service.bankaccount.BankAccountStateEnum;

import java.time.Instant;
import java.util.ArrayList;



import static software.jevera.service.bankaccount.BankAccountStateEnum.CREATED;

public class BankAccount {

    private Long id;
    private Instant creationDate;
    private Integer balance;
    private Integer hashBankAccountNumber;
    private User owner;
    private BankAccountStateEnum currentState = CREATED;
    private ArrayList<Card> cards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getHashBankAccountNumber() {
        return hashBankAccountNumber;
    }

    public void setHashBankAccountNumber(Integer hashBankAccountNumber) {
        this.hashBankAccountNumber = hashBankAccountNumber;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BankAccountStateEnum getCurrentState() {
        return currentState;
    }

    public void setCurrentState(BankAccountStateEnum currentState) {
        this.currentState = currentState;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }


}
