package software.jevera.service.bankaccount;

import org.springframework.stereotype.Component;
import software.jevera.domain.BankAccount;
import software.jevera.domain.User;
import software.jevera.exceptions.CannotRestoreBankAccount;

import static software.jevera.service.bankaccount.BankAccountStateEnum.BLOCKED_BY_USER;
import static software.jevera.service.bankaccount.BankAccountStateEnum.RESTORED;
@Component
public class BlockedByUser extends BankAccountState {
    @Override
    public BankAccountStateEnum getStateName() {
        return BLOCKED_BY_USER;
    }

    @Override
    public void restoreByUser(BankAccount bankAccount, User user) {
        if(bankAccount.getCurrentState().equals(getStateName())) {
            bankAccount.setCurrentState(RESTORED);
        }else{
            throw new CannotRestoreBankAccount("You have not permission to restore account!");
        }

    }
}
