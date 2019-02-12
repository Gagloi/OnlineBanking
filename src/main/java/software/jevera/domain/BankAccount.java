package main.java.software.jevera.domain;

import main.java.software.jevera.service.bankaccount.BankAccountState;
import main.java.software.jevera.service.bankaccount.BankAccountStateEnum;

import java.time.Instant;

import static main.java.software.jevera.service.bankaccount.BankAccountStateEnum.CREATED;

public class BankAccount {

    private Long id;
    private Instant creationDate;
    private Integer count;
    private Integer hashBankAccountNumber;
    private User owner;
    private BankAccountStateEnum state = CREATED;

    public BankAccountStateEnum getCurrentState() {
        return state;
    }

    public void setCurrentState(BankAccountStateEnum state) {
        this.state = state;
    }

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
}
