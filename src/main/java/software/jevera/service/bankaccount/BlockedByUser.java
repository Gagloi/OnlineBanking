package main.java.software.jevera.service.bankaccount;

import main.java.software.jevera.domain.BankAccount;

import static main.java.software.jevera.service.bankaccount.BankAccountStateEnum.BLOCKED_BY_USER;
import static main.java.software.jevera.service.bankaccount.BankAccountStateEnum.RESTORED;

public class BlockedByUser extends BankAccountState {
    @Override
    public BankAccountStateEnum getStateName() {
        return BLOCKED_BY_USER;
    }

    @Override
    public void restore(BankAccount bankAccount) {
        bankAccount.setCurrentState(RESTORED);
    }

}