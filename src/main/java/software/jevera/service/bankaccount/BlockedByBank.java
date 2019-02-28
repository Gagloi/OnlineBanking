package software.jevera.service.bankaccount;

import software.jevera.domain.BankAccount;

import static software.jevera.service.bankaccount.BankAccountStateEnum.BLOCKED_BY_BANK;
import static software.jevera.service.bankaccount.BankAccountStateEnum.RESTORED;

public class BlockedByBank extends BankAccountState {
    @Override
    public BankAccountStateEnum getStateName() {
        return BLOCKED_BY_BANK;
    }

    @Override
    public void restore(BankAccount bankAccount) {
        bankAccount.setCurrentState(RESTORED);
    }
}
