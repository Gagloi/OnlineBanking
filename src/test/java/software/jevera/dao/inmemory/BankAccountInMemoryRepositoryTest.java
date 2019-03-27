package software.jevera.dao.inmemory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import software.jevera.domain.BankAccount;
import software.jevera.domain.User;
import software.jevera.service.bankaccount.BankAccountStateEnum;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static software.jevera.service.bankaccount.BankAccountStateEnum.ACTIVE;


public class BankAccountInMemoryRepositoryTest
{

    private BankAccountInMemoryRepository bankAccountInMemoryRepository = new BankAccountInMemoryRepository();

    @Test
    public void save()
    {
        User user = new User("pwd", "user");
        BankAccount bankAccount = new BankAccount(1234L, Instant.now(), 10,90 , user , ACTIVE, null);
        BankAccount saved = bankAccountInMemoryRepository.save(bankAccount);
        Optional<BankAccount> byUser = bankAccountInMemoryRepository.findByUser(user);
        assertEquals(byUser.get(), bankAccount);
    }

    private BankAccount createBankAcc(String login, Long id){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(id);
        bankAccount.setOwner(new User("pwd", login ));
        return bankAccount;
    }

    @Test
    public void findByUser()
    {

        User user = new User("pwd", "user007");

        BankAccount bankAccount = new BankAccount(1234L, Instant.now(), 10,90 , user ,ACTIVE, null);
        List<BankAccount> bankAccounts = asList(bankAccount, createBankAcc("user1", 1L), createBankAcc("user2", 2L), createBankAcc("user3", 3L));
        bankAccounts.forEach(bankAccountInMemoryRepository::save);
        BankAccount bankAccount1 = bankAccountInMemoryRepository.findByUser(user).get();
        assertEquals(bankAccount1, bankAccount);
    }

    @Test
    public void findById()
    {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        List<BankAccount> bankAccounts = asList(
                    bankAccount,
                    createBankAcc("user1", 2L),
                    createBankAcc("user2", 3L),
                    createBankAcc("user3", 4L));
        bankAccounts.forEach(bankAccountInMemoryRepository::save);
        Optional<BankAccount> res = bankAccountInMemoryRepository.findById(1L);
        assertEquals(res.get(), bankAccount);

    }

    @Test
    public void delete()
    {
        List<BankAccount> bankAccounts = asList(
                createBankAcc("user007", 1L),
                createBankAcc("user1", 2L),
                createBankAcc("user2", 3L),
                createBankAcc("user3", 4L));
        bankAccounts.forEach(bankAccountInMemoryRepository::save);
        bankAccountInMemoryRepository.delete(1L);


        assertEquals(Optional.empty(), bankAccountInMemoryRepository.findById(1L));

    }
}