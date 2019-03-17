package software.jevera.dao.inmemory;

import java.time.Instant;
import java.util.Optional;

import org.junit.Test;

import software.jevera.domain.BankAccount;
import software.jevera.domain.User;

import static org.junit.Assert.*;
import static software.jevera.service.bankaccount.BankAccountStateEnum.CREATED;

public class BankAccountInMemoryRepositoryTest
{

    private BankAccountInMemoryRepository bankAccountInMemoryRepository = new BankAccountInMemoryRepository();

    @Test
    public void save()
    {
        User user = new User("pwd", "user");
        BankAccount bankAccount = new BankAccount(1234L, Instant.now(), 10,90 , user ,CREATED, null);
        BankAccount saved = bankAccountInMemoryRepository.save(bankAccount);
        Optional<BankAccount> byUser = bankAccountInMemoryRepository.findByUser(user);
        assertEquals(byUser.get(), bankAccount);
    }


    @Test
    public void findByUser()
    {
        User user = new User("pwd", "user");
        BankAccount bankAccount = new BankAccount(1234L, Instant.now(), 10,90 , user ,CREATED, null);
        bankAccountInMemoryRepository.save(bankAccount);
        BankAccount bankAccount1 = bankAccountInMemoryRepository.findByUser(user).get();
        assertEquals(bankAccount1, bankAccount);
    }

    @Test
    public void findById()
    {
    }

    @Test
    public void chargeBalance()
    {
    }

    @Test
    public void getMoney()
    {
    }

    @Test
    public void doTransition()
    {
    }

    @Test
    public void delete()
    {
    }
}