package main.java.software.jevera.service.bankaccount;

import main.java.software.jevera.dao.BankAccountRepository;
import main.java.software.jevera.domain.BankAccount;
import main.java.software.jevera.domain.Card;
import main.java.software.jevera.domain.User;
import main.java.software.jevera.exceptions.BusinessException;

import java.util.List;

public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final StateMachine stateMachine;

    public BankAccountService(BankAccountRepository bankAccountRepository, StateMachine stateMachine) {
        this.bankAccountRepository = bankAccountRepository;
        this.stateMachine = stateMachine;
    }

    public BankAccountRepository getBankAccountRepository() {
        return bankAccountRepository;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public BankAccount createBankAccount(BankAccount bankAccount, User owner){
        assertIsNull(bankAccount.getId(), "Id already exists.");
        bankAccount.setOwner(owner);
        return bankAccountRepository.save(bankAccount);
    }

    private void assertIsNull(Long id, String message) {
        if (id != null) {
            throw new BusinessException(message);
        }
    }

    private BankAccount getBankAccount(Long id){
        return bankAccountRepository.findById(id).orElseThrow();
    }

    public void create(Long id){
        BankAccount bankAccount = getBankAccount(id);
        stateMachine.create(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void confirm(Long id){
        BankAccount bankAccount = getBankAccount(id);
        stateMachine.confirm(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void reject(Long id){
        BankAccount bankAccount = getBankAccount(id);
        stateMachine.reject(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void restore(Long id){
        BankAccount bankAccount = getBankAccount(id);
        stateMachine.restore(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void blockByBank(Long id){
        BankAccount bankAccount = getBankAccount(id);
        stateMachine.blockByBank(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void blockByUser(Long id){
        BankAccount bankAccount = getBankAccount(id);
        stateMachine.blockByUser(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void addCard(Card card, User Owner, Long idOfAccount){
        BankAccount bankAccount = getBankAccount(idOfAccount);
        bankAccountRepository.addCard(bankAccount, card);
        bankAccountRepository.save(bankAccount);
    }

    public BankAccount findByUser(User user){
        return bankAccountRepository.findByUser(user);
    }


}
