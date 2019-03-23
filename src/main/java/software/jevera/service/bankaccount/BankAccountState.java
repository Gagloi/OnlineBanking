package software.jevera.service.bankaccount;

import software.jevera.domain.BankAccount;
import software.jevera.domain.User;
import software.jevera.exceptions.StateTransitionException;

public abstract class BankAccountState {

    private void noStateTransition(BankAccount bankAccount){
        throw new StateTransitionException("Illegal state transition " + bankAccount);
    }

    public abstract BankAccountStateEnum getStateName();

    public void blockByBank(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

    public void blockByUser(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

    public void restoreByBank(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

    public void restoreByUser(BankAccount bankAccount, User user){
        noStateTransition(bankAccount);
    }

    public void activate(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

}
