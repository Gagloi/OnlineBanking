package software.jevera.service.bankaccount;

import software.jevera.domain.BankAccount;
import software.jevera.exceptions.StateTransitionException;

public abstract class BankAccountState {

    private void noStateTransition(BankAccount bankAccount){
        throw new StateTransitionException("Illegal state transition " + bankAccount);
    }

    public abstract BankAccountStateEnum getStateName();

    public void confirm(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

    public void reject(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

    public void blockByBank(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

    public void blockByUser(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

    public void restore(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

    public void create(BankAccount bankAccount){
        noStateTransition(bankAccount);
    }

}
