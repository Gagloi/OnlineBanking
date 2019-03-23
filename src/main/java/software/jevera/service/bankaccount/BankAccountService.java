package software.jevera.service.bankaccount;

import lombok.SneakyThrows;
import software.jevera.annotation.Blockable;
import software.jevera.annotation.BlockableListener;
import software.jevera.dao.BankAccountRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.exceptions.BusinessException;

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
        return bankAccountRepository.findById(id).orElseThrow(()-> new BusinessException("Did not found Bank Account by id"));
    }

    private BankAccount getBankAccountByUser(User owner){
        return bankAccountRepository.findByUser(owner).orElseThrow(() -> new BusinessException("Cannot get Bank Account by user!"));
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

    @Blockable
    @SneakyThrows
    public void chargeBalance(Long id, Integer amount){
        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new BusinessException("Can not find Bank Account"));
        BlockableListener.blocked(BankAccount.class, bankAccount);
        if(isNegative(amount)) {
            bankAccount.setBalance(bankAccount.getBalance() + amount);
            bankAccountRepository.save(bankAccount);
        }else{
            throw new BusinessException("Низья отрицательное число ворюга!");
        }
        //bankAccountRepository.chargeBalance(id, amount);
    }

    public void delete(Long id){
        bankAccountRepository.delete(id);
    }

    @Blockable
    @SneakyThrows
    public void doTransition(User fromTransaction, Card card, Integer amount) {
        BankAccount from = findByUser(fromTransaction);
        BankAccount to = findByUser(card.getOwner());
        BlockableListener.blocked(BankAccount.class, from);
        BlockableListener.blocked(BankAccount.class, to);
        if(from.getBalance() > amount){
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            bankAccountRepository.save(from);
            bankAccountRepository.save(to);
        }else{
            throw new BusinessException("Ты бомж!");
        }

    }
    @Blockable
    @SneakyThrows
    public void getMoney(String cvv, String cardNumber, User owner, Integer amount){
        BankAccount bankAccount = findByUser(owner);
        BlockableListener.blocked(BankAccount.class, bankAccount);
        Card card = bankAccount.getCards().stream()
                .filter(it -> (it.getCardNumber().equals(cardNumber) && it.getCvv().equals(cvv)))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Bad credentials"));
        if(bankAccount.getBalance() > amount){
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
        }else{
            throw new BusinessException("Ты бомж!");
        }
        //bankAccountRepository.getMoney(cvv, cardNumber, owner, amount);
    }

    private boolean isNegative(Integer amount){
        return amount > 0;
    }

}
