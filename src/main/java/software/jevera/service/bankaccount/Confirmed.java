package software.jevera.service.bankaccount;


import software.jevera.domain.BankAccount;

import static software.jevera.service.bankaccount.BankAccountStateEnum.*;

public class Confirmed extends BankAccountState {

    @Override
    public BankAccountStateEnum getStateName() {
        return CONFIRMED;
    }

    @Override
    public void blockByBank(BankAccount bankAccount) {
        bankAccount.setCurrentState(BLOCKED_BY_BANK);
    }

    @Override
    public void blockByUser(BankAccount bankAccount) {
        bankAccount.setCurrentState(BLOCKED_BY_USER);
    }

}
