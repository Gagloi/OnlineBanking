package main.java.software.jevera.dao;

import main.java.software.jevera.domain.BankAccount;
import main.java.software.jevera.domain.Card;
import main.java.software.jevera.domain.User;

import java.util.List;

public interface CardRepository {

    Card save(Card card);

    List<Card> findCardsByUser(User user);

    List<Card> findAll();

}
