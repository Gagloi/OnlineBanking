package software.jevera.dao.inmemory;


import software.jevera.dao.BankAccountRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.exceptions.BusinessException;
import software.jevera.exceptions.CannotRestoreBankAccount;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
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
    public Optional<BankAccount> findByUser(User user) {
        return this.bankAccounts.stream().filter(it -> it.getOwner().equals(user)).findAny();
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccounts.stream().filter(it -> it.getId().equals(id)).findAny();
    }

    @Override
    public void delete(Long id) {
        bankAccounts = bankAccounts.stream().filter(it -> !it.equals(findById(id).get())).collect(Collectors.toSet());
    }

}
