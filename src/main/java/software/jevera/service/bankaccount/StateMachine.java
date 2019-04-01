package software.jevera.service.bankaccount;

import lombok.RequiredArgsConstructor;
import software.jevera.domain.BankAccount;
import software.jevera.domain.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class StateMachine {

    private final Map<BankAccountStateEnum, BankAccountState> states = new ConcurrentHashMap<>();

    public StateMachine(List<BankAccountState> bankAccountState){
        bankAccountState.forEach(state -> states.put(state.getStateName(), state));
    }

    private BankAccountState getState(BankAccount bankAccount) {
        return states.get(bankAccount.getCurrentState());
    }

    void activate(BankAccount bankAccount){
        getState(bankAccount).activate(bankAccount);
    }

    void blockByBank(BankAccount bankAccount){
        getState(bankAccount).blockByBank(bankAccount);
    }

    void blockByUser(BankAccount bankAccount){
        System.out.println(getState(bankAccount));
        getState(bankAccount).blockByUser(bankAccount);
    }

    void restoreByBank(BankAccount bankAccount){
        getState(bankAccount).restoreByBank(bankAccount);
    }

    void restoreByUser(User user, BankAccount bankAccount){
        getState(bankAccount).restoreByUser(bankAccount, user);
    }


}
