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
    public void addCard(BankAccount bankAccount, Card card) {
        bankAccounts.stream().filter(it -> it.equals(bankAccount)).findAny().get().getCards().add(card);
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccounts.stream().filter(it -> it.getId().equals(id)).findAny();
    }
}
