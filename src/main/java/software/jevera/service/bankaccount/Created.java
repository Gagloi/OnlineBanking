package software.jevera.service.bankaccount;

import software.jevera.domain.BankAccount;

import static software.jevera.service.bankaccount.BankAccountStateEnum.*;

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
