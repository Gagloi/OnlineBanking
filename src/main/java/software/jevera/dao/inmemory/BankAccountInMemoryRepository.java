package software.jevera.dao.inmemory;

import software.jevera.dao.BankAccountRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.exceptions.BusinessException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BankAccountInMemoryRepository implements BankAccountRepository {

    private Set<BankAccount> bankAccounts = new HashSet<>();

    private AtomicLong counter = new AtomicLong(0);

    @Override
    public BankAccount save(BankAccount bankAccount) {
        if (bankAccount.getId() == null) {
            bankAccount.setId(counter.incrementAndGet());
        }
        bankAccounts.add(bankAccount);
        return bankAccount;
    }

    @Override
    public List<BankAccount> findAll() {
        return new ArrayList<>(bankAccounts);
    }

    @Override
    public BankAccount findByUser(User user) {
        return bankAccounts.stream().filter(it -> it.getOwner().equals(user)).findFirst().get();
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccounts.stream().filter(it -> it.getId().equals(id)).findAny();
    }

    @Override
    public void chargeBalance(Long id, Integer amount) {
        BankAccount bankAccount = findById(id).orElseThrow(() -> new BusinessException("Can not find Bank Account"));
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        save(bankAccount);
    }

    @Override
    public void getMoney(String cvv, String cardNumber, User owner, Integer amount) {
        BankAccount bankAccount = bankAccounts.stream()
                                    .filter(it -> it.getOwner().equals(owner))
                                    .findFirst()
                                    .orElseThrow(() -> new BusinessException("Can not find Bank Account"));
        Card card = bankAccount.getCards().stream()
                .filter(it -> (it.getCardNumber().equals(cardNumber) && it.getCvv().equals(cvv)))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Can not find card"));
        if((bankAccount.getBalance() > amount) && isNull(card)){
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            save(bankAccount);
        }else{
            throw new BusinessException("НЕХВАТАЕТ ГРОШЕЙ!");
        }
    }

    @Override
    public void doTransition(User fromTransaction, Card card, Integer amount) {
        BankAccount from = findByUser(fromTransaction);
        BankAccount to = findByUser(card.getOwner());

        if(from.getBalance() > amount){
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            save(from);
            save(to);
        }else{
            throw new BusinessException("НЕХВАТАЕТ ГРОШЕЙ!");
        }

    }

    private boolean isNull(Object obj) {
        return obj == null;
    }
}
