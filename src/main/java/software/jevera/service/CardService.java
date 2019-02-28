package software.jevera.service;

import lombok.Getter;
import software.jevera.dao.CardRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.util.List;

public class CardService {

    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void addCard(Card card, BankAccount bankAccount){
        bankAccount.getCards().add(card);
        cardRepository.save(card);
    }

    public List<Card> getCardByUser(User user){
        return cardRepository.findCardsByUser(user);
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }

}
