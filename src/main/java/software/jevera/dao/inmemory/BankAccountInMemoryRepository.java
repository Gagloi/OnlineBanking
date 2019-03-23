package software.jevera.dao.inmemory;


import software.jevera.dao.BankAccountRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.exceptions.BusinessException;
import software.jevera.exceptions.CannotRestoreBankAccount;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class BankAccountInMemoryRepository implements BankAccountRepository {

    private Set<BankAccount> bankAccounts = new HashSet<>();

    private AtomicLong counter = new AtomicLong(0);

    @Override
    public BankAccount save(BankAccount bankAccount) {
        if (bankAccount.getId() == null) {
            bankAccount.setId(counter.incrementAndGet());
        }
        bankAccounts.add(bankAccount);
        return bankAccount;
    }


    @Override
    public Optional<BankAccount> findByUser(User user) {
        return this.bankAccounts.stream().filter(it -> it.getOwner().equals(user)).findAny();
    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccounts.stream().filter(it -> it.getId().equals(id)).findAny();
    }

    @Override
    public void chargeBalance(Long id, Integer amount) {
        BankAccount bankAccount = findById(id).orElseThrow(() -> new BusinessException("Can not find Bank Account"));
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        save(bankAccount);
    }

//    @Override
//    public void getMoney(String cvv, String cardNumber, User owner, Integer amount) {
//        BankAccount bankAccount = findByUser(owner).orElseThrow(() -> new BusinessException("Can not find Bank Account"));
//        Card card = bankAccount.getCards().stream()
//                .filter(it -> (it.getCardNumber().equals(cardNumber) && it.getCvv().equals(cvv)))
//                .findFirst()
//                .orElseThrow(() -> new BusinessException("Can not find card"));
//        if((bankAccount.getBalance() > amount) && isNull(card)){
//            bankAccount.setBalance(bankAccount.getBalance() - amount);
//            save(bankAccount);
//        }else{
//            throw new BusinessException("НЕХВАТАЕТ ГРОШЕЙ!");
//        }
//    }

//    @Override
//    public void doTransition(User fromTransaction, Card card, Integer amount) {
//        BankAccount from = findByUser(fromTransaction).orElseThrow(() -> new BusinessException("Can not find Bank Account"));
//        BankAccount to = findByUser(card.getOwner()).orElseThrow(() -> new BusinessException("Can not find Bank Account"));
//
//        if(from.getBalance() > amount){
//            from.setBalance(from.getBalance() - amount);
//            to.setBalance(to.getBalance() + amount);
//            save(from);
//            save(to);
//        }else{
//            throw new BusinessException("НЕХВАТАЕТ ГРОШЕЙ!");
//        }
//        save(findByUser(fromTransaction).get());
//        save(findByUser(card.getOwner()).get());
//
//    }

    @Override
    public void delete(Long id) {
        bankAccounts = bankAccounts.stream().filter(it -> it.equals(findById(id).get())).collect(Collectors.toSet());
    }

//    private boolean isNull(Object obj) {
//        return obj == null;
//    }
}
