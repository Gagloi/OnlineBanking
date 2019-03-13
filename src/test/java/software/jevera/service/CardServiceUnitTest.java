package software.jevera.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import software.jevera.dao.BankAccountRepository;
import software.jevera.dao.CardRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.service.bankaccount.BankAccountService;
import software.jevera.service.bankaccount.StateMachine;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CardServiceUnitTest
{

    private CardService cardService;

    private CardRepository cardRepository;

    private List<Card> cards;

    @Before
    public void before(){
        cardRepository = mock(CardRepository.class);
        cardService = new CardService(cardRepository);
        cards.add(new Card(new User(), "0", "435", Instant.now()));
    }


    @Test
    public void addCard()
    {
        User owner = new User();
        Card card = new Card(owner, "1", "333", Instant.now());
        doAnswer((i) -> {
            assertEquals(card, i.getArgument(0));
            assertEquals(card.getOwner(), i.getArgument(1));
            return null;
        }).when(cardRepository).addCard(any(User.class), any(Card.class));
        cardService.addCard(card, owner);
    }

    @Test
    public void getCardByUser()
    {
        User user = new User();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(user, "1", "333", Instant.now()));
        when(cardRepository.findCardsByUser(user)).thenReturn(cards);
        assertEquals(cards, cardRepository.findCardsByUser(user));
    }

    @Test
    public void findAll()
    {
        User user = new User();
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(user, "1", "333", Instant.now()));
        when(cardRepository.findCardsByUser(user)).thenReturn(cards);
        assertEquals(cards, cardRepository.findCardsByUser(user));
    }
}