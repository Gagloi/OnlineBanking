package software.jevera.service.bankaccount;

import org.springframework.stereotype.Component;
import software.jevera.domain.BankAccount;

import static software.jevera.service.bankaccount.BankAccountStateEnum.*;

@Component
public class Restored extends BankAccountState {
    @Override
    public BankAccountStateEnum getStateName() {
        return RESTORED;
    }

    @Override
    public void activate(BankAccount bankAccount) {
        bankAccount.setCurrentState(ACTIVE);
    }
}
