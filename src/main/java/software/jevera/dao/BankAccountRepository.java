package main.java.software.jevera.dao;

import main.java.software.jevera.domain.BankAccount;
import main.java.software.jevera.domain.User;

import java.util.Optional;

public interface BankAccountRepository {

    BankAccount save(BankAccount bankAccount);

    BankAccount findAll();

    BankAccount findByUser(User user);

    Optional<BankAccount> findById(Long id);

}
