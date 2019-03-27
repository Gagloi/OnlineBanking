package software.jevera.dao;

import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {

    BankAccount save(BankAccount bankAccount);

    Optional<BankAccount> findByUser(User user);

    Optional<BankAccount> findById(Long id);

    void delete(Long delete);

}
