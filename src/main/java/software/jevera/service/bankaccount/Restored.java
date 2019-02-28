package software.jevera.service.bankaccount;

import software.jevera.domain.BankAccount;

import static software.jevera.service.bankaccount.BankAccountStateEnum.CONFIRMED;
import static software.jevera.service.bankaccount.BankAccountStateEnum.RESTORED;

public class Restored extends BankAccountState {
    @Override
    public BankAccountStateEnum getStateName() {
        return RESTORED;
    }

    @Override
    public void confirm(BankAccount bankAccount) {
        bankAccount.setCurrentState(CONFIRMED);
    }
}
