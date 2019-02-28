package software.jevera.dao;

import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.util.List;

public interface CardRepository {

    Card save(Card card);

    List<Card> findCardsByUser(User user);

    List<Card> findAll();

}
