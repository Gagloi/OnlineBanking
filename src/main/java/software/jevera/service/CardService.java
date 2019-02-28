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

//ADDCARD add USER!!! find by user
    public void addCard(Card card, BankAccount bankAccount){
        bankAccount.getCards().add(card);
        cardRepository.save(card);
    }
//ADDCARD add USER!!! find by user
    public List<Card> getCardByUser(User user){
        return cardRepository.findCardsByUser(user);
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }

}
