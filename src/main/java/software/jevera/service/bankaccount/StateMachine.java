package main.java.software.jevera.service.bankaccount;

import main.java.software.jevera.domain.BankAccount;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StateMachine {

    private final Map<BankAccountStateEnum, BankAccountState> states = new ConcurrentHashMap<>();

    public StateMachine(List<BankAccountState> bankAccountState){
        bankAccountState.forEach(state -> states.put(state.getStateName(), state));
    }

    public BankAccountState getState(BankAccount bankAccount) {
        return states.get(bankAccount.getCurrentState());
    }

    public void confirm(BankAccount bankAccount){
        getState(bankAccount).confirm(bankAccount);
    }

    public void reject(BankAccount bankAccount){
        getState(bankAccount).reject(bankAccount);
    }

    public void blockByBank(BankAccount bankAccount){
        getState(bankAccount).blockByBank(bankAccount);
    }

    public void blockByUser(BankAccount bankAccount){
        getState(bankAccount).blockByUser(bankAccount);
    }

    public void restore(BankAccount bankAccount){
        getState(bankAccount).restore(bankAccount);
    }


}
