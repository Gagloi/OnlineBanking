package software.jevera.service.bankaccount;

import software.jevera.dao.BankAccountRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.exceptions.BusinessException;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
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

    private BankAccount getBankAccountById(Long id){
        return bankAccountRepository.findById(id).orElseThrow(()-> new BusinessException("Did nit found bBank Account by id"));
    }

    private BankAccount getBankAccountByUser(User owner){
        return bankAccountRepository.findByUser(owner).orElseThrow(() -> new BusinessException("Cannot get Bank Account by user!"));
    }

    public void confirm(Long id){
        BankAccount bankAccount = getBankAccountById(id);
        stateMachine.confirm(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void reject(Long id){
        BankAccount bankAccount = getBankAccountById(id);
        stateMachine.reject(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void restoreByBank(Long id){
        BankAccount bankAccount = getBankAccountById(id);
        stateMachine.restoreByBank(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void restoreByUser(User user){
        BankAccount bankAccount = getBankAccountByUser(user);
        stateMachine.restoreByUser(user, bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void blockByBank(Long id){
        BankAccount bankAccount = getBankAccountById(id);
        stateMachine.blockByBank(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void blockByUser(User user){
        BankAccount bankAccount = getBankAccountByUser(user);
        stateMachine.blockByUser(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public BankAccount findByUser(User user){
        return bankAccountRepository.findByUser(user).orElseThrow(() -> new BusinessException("Can not find bank account by user"));
    }

    public void chargeBalance(Long id, Integer amount){
        bankAccountRepository.chargeBalance(id, amount);
    }

    public void delete(Long id){
        bankAccountRepository.delete(id);
    }

    public void doTransition(User fromTransaction, Card card, Integer amount) {
        BankAccount from = findByUser(fromTransaction);
        BankAccount to = findByUser(card.getOwner());

        if(from.getBalance() > amount){
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            bankAccountRepository.save(from);
            bankAccountRepository.save(to);
        }else{
            throw new BusinessException("НЕХВАТАЕТ ГРОШЕЙ!");
        }
        //bankAccountRepository.doTransition(fromTransaction,card,amount);
    }

    public void getMoney(String cvv, String cardNumber, User owner, Integer amount){
        BankAccount bankAccount = findByUser(owner);
        Card card = bankAccount.getCards().stream()
                .filter(it -> (it.getCardNumber().equals(cardNumber) && it.getCvv().equals(cvv)))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Can not find card"));
        if((bankAccount.getBalance() > amount) && isNull(card)){
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
        }else{
            throw new BusinessException("НЕХВАТАЕТ ГРОШЕЙ!");
        }
        //bankAccountRepository.getMoney(cvv, cardNumber, owner, amount);
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }



}
