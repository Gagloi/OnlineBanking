package software.jevera.dao.inmemory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import software.jevera.dao.BankAccountRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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
                new Card(kekUser,"23347", "234", Instant.now()),
                new Card(kekUser,"233474", "234", Instant.now()),
                new Card(lolUser,"762346", "234", Instant.now())

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

//    @Test
//    public void addCard() {
//        Card card = new Card("1234", "234", Instant.now());
//        User user = new User("123", "Ioi");
//        BankAccount bankAccount = new BankAccount();
//        bankAccount.setOwner(user);
//
//        ArrayList<Card> cards = new ArrayList<>();
//        cards.add(new Card("123423", "423", Instant.now()));
//        bankAccount.setCards(cards);
//
//        cardInMemoryRepository.addCard(user, card);
//        assertEquals(card, cardInMemoryRepository.findCardsByUser(user));
//    }
}