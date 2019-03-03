package software.jevera.service.bankaccount;

import software.jevera.domain.BankAccount;

import static software.jevera.service.bankaccount.BankAccountStateEnum.REJECTED;
import static software.jevera.service.bankaccount.BankAccountStateEnum.RESTORED;

public class Rejected extends BankAccountState {
    @Override
    public BankAccountStateEnum getStateName() {
        return REJECTED;
    }

    @Override
    public void restoreByBank(BankAccount bankAccount) {
        bankAccount.setCurrentState(RESTORED);
    }
}
