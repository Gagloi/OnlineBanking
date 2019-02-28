package software.jevera.dao.inmemory;

import software.jevera.dao.CardRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class CardInMemoryRepository implements CardRepository {

    private Set<Card> cards = new HashSet<>();

    private AtomicLong counter = new AtomicLong(0);


    @Override
    public Card save(Card card) {
        cards.add(card);
        return card;
    }

    @Override
    public List<Card> findCardsByUser(User user) {
        return cards.stream().filter(card -> card.getOwner().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<Card> findAll() {
        return new ArrayList<>(cards);
    }

}
