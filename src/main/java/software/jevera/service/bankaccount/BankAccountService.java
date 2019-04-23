package software.jevera.service.bankaccount;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.jevera.dao.BankAccountRepository;
import software.jevera.dao.CardRepository;
import software.jevera.dao.inmemory.CardInMemoryRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.domain.dto.TopUpDto;
import software.jevera.exceptions.BusinessException;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final StateMachine stateMachine;
    private final CardRepository cardRepository;



    public BankAccountRepository getBankAccountRepository() {
        return bankAccountRepository;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public BankAccount createBankAccount(BankAccount bankAccount, User owner){
        assertIsNull(bankAccount.getId(), "Id already exists.");
        bankAccount.setOwner(owner);
        return bankAccountRepository.save(bankAccount);
    }

    private void assertIsNull(Long id, String message) {
        if (id != null) {
            throw new BusinessException(message);
        }
    }

    private BankAccount getBankAccountById(Long id){
        return bankAccountRepository.findById(id).orElseThrow(()-> new BusinessException("Did not found Bank Account by id"));
    }

    private BankAccount getBankAccountByUser(User owner){
        return bankAccountRepository.findByUser(owner).orElseThrow(() -> new BusinessException("Cannot get Bank Account by user or no BankAccount!"));
    }

    public void restoreByBank(Long id){
        BankAccount bankAccount = getBankAccountById(id);
        stateMachine.restoreByBank(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void restoreByUser(User user){
        BankAccount bankAccount = getBankAccountByUser(user);
        stateMachine.restoreByUser(user, bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void blockByBank(Long id){
        BankAccount bankAccount = getBankAccountById(id);
        stateMachine.blockByBank(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void blockByUser(User user){
        BankAccount bankAccount = getBankAccountByUser(user);
        stateMachine.blockByUser(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public void activate(Long id){
        BankAccount bankAccount = getBankAccountById(id);
        stateMachine.activate(bankAccount);
        bankAccountRepository.save(bankAccount);
    }

    public BankAccount findByUser(User user){
        return bankAccountRepository.findByUser(user).orElseThrow(() -> new BusinessException("Can not find bank account by user"));
    }


    public void topUpTheBalance(TopUpDto topUpDto, User user){
        BankAccount bankAccount = bankAccountRepository.findByUser(user).orElseThrow(() -> new BusinessException("Can not find Bank Account"));
        isBlocked(bankAccount);
        Optional<Card> card = bankAccount.getCards()
                .stream()
                .filter(it -> it.getCardNumber().equals(topUpDto.getCardNumber()) && it.getCvv().equals(topUpDto.getCvv()))
                .findAny();
        if (card.isPresent()) {
            if (isNegative(topUpDto.getAmount())) {
                bankAccount.setBalance(bankAccount.getBalance() + topUpDto.getAmount());
                bankAccountRepository.save(bankAccount);
            } else {
                throw new BusinessException("Низья отрицательное число ворюга!");
            }
        }else{
            throw new BusinessException("Ca not find card");
        }
    }

    public void delete(Long id){
        bankAccountRepository.delete(id);
    }


    public void doTransition(User fromTransaction, Card card, Integer amount) {
        BankAccount from = findByUser(fromTransaction);
        BankAccount to = findByCardNumber(card.getCardNumber());
        isBlocked(from);
        isBlocked(to);
        if(from.getBalance() >= amount){
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            bankAccountRepository.save(from);
            bankAccountRepository.save(to);
        }else{
            throw new BusinessException("Ты бомж!");
        }

    }

    public void getMoney(String cvv, String cardNumber, User owner, Integer amount){
        BankAccount bankAccount = findByUser(owner);
        isBlocked(bankAccount);
        Optional<Card> myCard = bankAccount.getCards().stream()
                .filter(it -> (it.getCardNumber().equals(cardNumber) && it.getCvv().equals(cvv)))
                .findAny();
        if (!myCard.isPresent()){
            throw new BusinessException("Bad credentials");
        }

        if(bankAccount.getBalance() >= amount){
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            bankAccountRepository.save(bankAccount);
        }else{
            throw new BusinessException("Ты бомж!");
        }

    }

    private boolean isNegative(Integer amount){
        return amount > 0;
    }


    private void isBlocked(BankAccount bankAccount){
        if(bankAccount.getCurrentState().equals(BankAccountStateEnum.BLOCKED_BY_BANK) || bankAccount.getCurrentState().equals(BankAccountStateEnum.BLOCKED_BY_USER))
        {
            throw new BusinessException("Твоя карта заблокирована!");
        }
    }

    public void addCard(User owner, Card card){
        BankAccount bankAccount = getBankAccountByUser(owner);
        if (bankAccount.getCards().isEmpty()){
            ArrayList<Card> cards = new ArrayList<>();
            cards.add(card);
            bankAccount.setCards(cards);
        }else{
            bankAccount.getCards().add(card);
        }
        card.setOwner(owner);
        cardRepository.save(card);
        bankAccountRepository.save(bankAccount);
    }

    private BankAccount findByCardNumber(String cardNumber){

        List<Card> cards = cardRepository.findAll();
        Optional<Card> mainCard = cards.stream().filter(card -> card.getCardNumber().equals(cardNumber)).findAny();
        System.out.println("lol" + cardNumber);
        return findByUser(mainCard.get().getOwner());
    }

}
