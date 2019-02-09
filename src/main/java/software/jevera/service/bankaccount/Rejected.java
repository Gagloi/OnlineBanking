package main.java.software.jevera.service.bankaccount;

import main.java.software.jevera.domain.BankAccount;

import static main.java.software.jevera.service.bankaccount.BankAccountStateEnum.REJECTED;
import static main.java.software.jevera.service.bankaccount.BankAccountStateEnum.RESTORED;

public class Rejected extends BankAccountState {
    @Override
    public BankAccountStateEnum getStateName() {
        return REJECTED;
    }

    @Override
    public void restore(BankAccount bankAccount) {
        bankAccount.setCurrentState(RESTORED);
    }
}
