package software.jevera.dao.inmemory;

import org.junit.Test;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.time.Instant;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class CardInMemoryRepositoryTest {

    private CardInMemoryRepository cardInMemoryRepository = new CardInMemoryRepository();


    @Test
    public void save() {
        Card card = new Card(new User(), "1234", "324", Instant.now());
        assertEquals(cardInMemoryRepository.save(card), card);

    }

    private Card createCard(String login){
        Card card = new Card(new User("ol", login),"1234", "234", Instant.now());
        return card;
    }

    @Test
    public void findCardsByUser() {
        User kekUser = new User("oi", "kek");
        User lolUser = new User("oi", "lol");
        List<Card> cards = asList(
                new Card(kekUser,"1234", "234", Instant.now()),
                new Card(kekUser,"1234", "234", Instant.now()),
                new Card(lolUser,"1234", "234", Instant.now())

        );

        List<Card> kek = asList(
                cards.get(0),
                cards.get(1)
        );

        List<Card> lol = asList(
               cards.get(2)
        );

        cards.forEach(cardInMemoryRepository::save);
        assertEquals(kek, cardInMemoryRepository.findCardsByUser(new User("kek", "kek")));
        assertEquals(lol, cardInMemoryRepository.findCardsByUser(new User("kek", "lol")));



    }

    @Test
    public void findAll() {
    }

    @Test
    public void addCard() {
    }
}