package software.jevera.dao;

import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {

    BankAccount save(BankAccount bankAccount);

    List<BankAccount> findAll();

    BankAccount findByUser(User user);

    Optional<BankAccount> findById(Long id);

    void chargeBalance(Long id, Integer amount);

    void getMoney(String cvv, String cardNumber, User owner, Integer amount);

    void doTransition(User fromTransaction, Card card, Integer amount);

}
