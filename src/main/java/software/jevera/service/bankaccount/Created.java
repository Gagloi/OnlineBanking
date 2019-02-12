package main.java.software.jevera.service.bankaccount;

import main.java.software.jevera.domain.BankAccount;

import static main.java.software.jevera.service.bankaccount.BankAccountStateEnum.*;

public class Created extends BankAccountState {


    @Override
    public BankAccountStateEnum getStateName() {
        return CREATED;
    }

    @Override
    public void confirm(BankAccount bankAccount) {
        bankAccount.setCurrentState(CONFIRMED);
    }

    @Override
    public void reject(BankAccount bankAccount) {
        bankAccount.setCurrentState(REJECTED);
    }
}
