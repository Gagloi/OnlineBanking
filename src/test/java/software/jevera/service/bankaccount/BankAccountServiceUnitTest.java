package software.jevera.service.bankaccount;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import software.jevera.dao.BankAccountRepository;
import software.jevera.dao.CardRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.service.CardService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static software.jevera.service.bankaccount.BankAccountStateEnum.*;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BankAccountService.class, StateMachine.class, BlockedByBank.class,
        Restored.class, Active.class, BlockedByUser.class, CardRepository.class})
public class BankAccountServiceUnitTest {

    @Autowired
    private BankAccountService bankAccountService;

    @MockBean
    private BankAccountRepository bankAccountRepository;

    @MockBean
    private StateMachine stateMachine;

    @MockBean
    private CardRepository cardRepository;

    @Test
    public void getBankAccountRepository() {
        assertEquals(bankAccountRepository, bankAccountService.getBankAccountRepository());
    }

    @Test
    public void getStateMachine() {
        assertEquals(bankAccountService.getStateMachine(), stateMachine);
    }

    @Test
    public void createBankAccount() {
        User user = new User("pwd", "user");
        User hacker = new User("pwd", "hacker");
        BankAccount bankAccount = new BankAccount(null, Instant.now(), 10, hacker ,ACTIVE, null);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.createBankAccount(bankAccount, user);
        assertNotNull(result);

        assertEquals(result.getOwner(), user);

        verify(bankAccountRepository).save(refEq(bankAccount));

        verifyNoMoreInteractions(bankAccountRepository);

    }


    @Test
    public void restoreByBank() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrentState(BLOCKED_BY_BANK);
        when(bankAccountRepository.findById(1234L)).thenReturn(Optional.of(bankAccount));
        bankAccountService.restoreByBank(1234L);
    }

    @Test
    public void restoreByUser() {
        User user = new User("pwd", "user");
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrentState(BLOCKED_BY_BANK);
        bankAccount.setOwner(user);
        when(bankAccountRepository.findByUser(user)).thenReturn(Optional.of(bankAccount));
        System.out.println(bankAccount.toString());
        bankAccountService.restoreByUser(user);
        System.out.println(bankAccount.toString());
    }

    @Test
    public void blockByBank() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrentState(BLOCKED_BY_BANK);
        bankAccount.setId(1234L);
        when(bankAccountRepository.findById(1234L)).thenReturn(Optional.of(bankAccount));
        bankAccountService.blockByBank(bankAccount.getId());
    }

    @Test
    public void blockByUser() {
        User user = new User("pwd", "user");
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrentState(ACTIVE);
        System.out.println("/////////" + bankAccount.getCurrentState());
        bankAccount.setOwner(user);
        when(bankAccountRepository.findByUser(user)).thenReturn(Optional.of(bankAccount));
        bankAccountService.blockByUser(bankAccount.getOwner());
        System.out.println("/////////" + bankAccount.getCurrentState());
    }

    @Test
    public void findByUser() {
        User user = new User();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(user);
        bankAccountRepository.save(bankAccount);
        when(bankAccountRepository.findByUser(user)).thenReturn(Optional.of(bankAccount));
        assertEquals(Optional.of(bankAccount), bankAccountRepository.findByUser(user));
    }

    @Test
    public void chargeBalance() {
        Long id = 1234L;
        Integer amount = 10;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(bankAccount));

        bankAccountService.topUpTheBalance(id, amount);
        System.out.println("///////////////////////" + bankAccount.toString());

        verify(bankAccountRepository).save(bankAccount);

    }

    @Test
    public void delete() {
        User user = new User("pwd", "user");
        BankAccount bankAccount = new BankAccount(1234L, Instant.now(), 10, user ,ACTIVE, null);
        bankAccountService.delete(1234L);
        verify(bankAccountRepository).delete(1234L);
    }

    @Test
    @SneakyThrows
    public void doTransition(){
        User user = new User("pwd", "user");//From user, who wants to do transition

        User userTo = new User("pwd", "userTo");//To user, where transition is implemented
        Card card = new Card(userTo, "1", "333", Instant.now());

        BankAccount bankAccountFrom = new BankAccount(); //From bank account, who wants to do transition
        bankAccountFrom.setBalance(11);
        bankAccountFrom.setCurrentState(ACTIVE);

        BankAccount bankAccountTo = new BankAccount(); //To bank account, where transition is implemented

        when(bankAccountRepository.findByUser(user)).thenReturn(Optional.of(bankAccountFrom));
        when(bankAccountRepository.findByUser(userTo)).thenReturn(Optional.of(bankAccountTo));
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card);
        when(cardRepository.findAll()).thenReturn(cards);
        bankAccountService.doTransition(user, card, 10);
        verify(bankAccountRepository).save(bankAccountFrom);
        verify(bankAccountRepository).save(bankAccountTo);

    }

    @Test
    public void getMoney(){
        User user = new User("pwd", "user");
        Card card = new Card(user, "1", "333", Instant.now());
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card);
        BankAccount bankAccount = new BankAccount(1L, Instant.now(), 11 , user, ACTIVE, cards);
        when(bankAccountRepository.findByUser(user)).thenReturn(Optional.of(bankAccount));
        bankAccountService.getMoney(card.getCvv(), card.getCardNumber(), user, 10);
        verify(bankAccountRepository).save(bankAccount);

    }

}