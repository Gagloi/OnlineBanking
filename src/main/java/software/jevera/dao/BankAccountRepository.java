package main.java.software.jevera.dao;

import main.java.software.jevera.domain.BankAccount;
import main.java.software.jevera.domain.Card;
import main.java.software.jevera.domain.User;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {

    BankAccount save(BankAccount bankAccount);

    List<BankAccount> findAll();

    BankAccount findByUser(User user);

    void addCard(BankAccount bankAccount, Card card);

    Optional<BankAccount> findById(Long id);

}
