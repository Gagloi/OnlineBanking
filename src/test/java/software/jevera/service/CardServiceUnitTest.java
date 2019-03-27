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
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


public class CardServiceUnitTest
{

    private CardService cardService;

    private CardRepository cardRepository;

    private List<Card> cards = new ArrayList<>();

    @Before
    public void before(){
        cardRepository = mock(CardRepository.class);
        cardService = new CardService(cardRepository);
    }


    @Test
    public void addCard()
    {
        User owner = new User();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(owner);
        Card card = new Card(owner, "1", "333", Instant.now());
        cards.add(card);
        when(cardRepository.findCardsByUser(owner)).thenReturn(cards);

        cardService.addCard(card, owner);
        verify(cardRepository).addCard(owner, card);
        verify(cardRepository).save(card);


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