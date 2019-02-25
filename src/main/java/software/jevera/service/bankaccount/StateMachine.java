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

    private BankAccountState getState(BankAccount bankAccount) {
        return states.get(bankAccount.getCurrentState());
    }

    void confirm(BankAccount bankAccount){
        getState(bankAccount).confirm(bankAccount);
    }

    void create(BankAccount bankAccount){
        getState(bankAccount).create(bankAccount);
    }

    void reject(BankAccount bankAccount){
        getState(bankAccount).reject(bankAccount);
    }

    void blockByBank(BankAccount bankAccount){
        getState(bankAccount).blockByBank(bankAccount);
    }

    void blockByUser(BankAccount bankAccount){
        getState(bankAccount).blockByUser(bankAccount);
    }

    void restore(BankAccount bankAccount){
        getState(bankAccount).restore(bankAccount);
    }


}
