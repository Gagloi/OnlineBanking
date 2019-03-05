package software.jevera.service.bankaccount;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import software.jevera.dao.BankAccountRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.User;

import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static software.jevera.service.bankaccount.BankAccountStateEnum.*;

public class BankAccountServiceUnitTest {

    private BankAccountService bankAccountService;

    private BankAccountRepository bankAccountRepository;

    private StateMachine stateMachine;

    @Before
    public void before(){
        bankAccountRepository = mock(BankAccountRepository.class);
        stateMachine = mock(StateMachine.class);
        bankAccountService = new BankAccountService(bankAccountRepository, stateMachine);
    }

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
        BankAccount bankAccount = new BankAccount(null, Instant.now(), 10,90 , hacker ,CREATED, null);
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.createBankAccount(bankAccount, user);
        assertNotNull(result);

        assertEquals(result.getOwner(), user);

        verify(bankAccountRepository).save(refEq(bankAccount));

        verifyNoMoreInteractions(bankAccountRepository);

    }

    @Test
    public void confirm() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrentState(CREATED);
        when(bankAccountRepository.findById(1234L)).thenReturn(Optional.of(bankAccount));
        bankAccountService.confirm(1234L);

    }

    @Test
    public void reject() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrentState(CREATED);
        when(bankAccountRepository.findById(1234L)).thenReturn(Optional.of(bankAccount));
        bankAccountService.reject(1234L);
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
        when(bankAccountRepository.findById(1234L)).thenReturn(Optional.of(bankAccount));
        bankAccountService.restoreByUser(user);
    }

    @Test
    public void blockByBank() {
    }

    @Test
    public void blockByUser() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrentState(BLOCKED_BY_BANK);
        when(bankAccountRepository.findById(1234L)).thenReturn(Optional.of(bankAccount));
        bankAccountService.restoreByUser(bankAccount.getOwner());
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
    }

    @Test
    public void delete() {
        User user = new User("pwd", "user");
        BankAccount bankAccount = new BankAccount(1234L, Instant.now(), 10,90 , user ,CREATED, null);
        bankAccountService.delete(1234L);
        assertNull(bankAccountService.findByUser(user));
    }
}