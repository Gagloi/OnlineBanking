package software.jevera.service.bankaccount;

import org.junit.Test;
import software.jevera.dao.BankAccountRepository;
import software.jevera.dao.inmemory.BankAccountInMemoryRepository;

import static org.junit.Assert.*;

public class BankAccountServiceTest {

    @Test
    public void getBankAccountRepository() {
        BankAccountRepository bankAccountRepository = new BankAccountInMemoryRepository();
    }
}