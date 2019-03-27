package software.jevera.service;

import lombok.Getter;
import software.jevera.dao.CardRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CardService {

    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

//    public void addCard(Card card, User user){
//        cardRepository.addCard(user, card);
//        cardRepository.save(card);
//    }
    public List<Card> getCardByUser(User user){
        return cardRepository.findCardsByUser(user);
    }

    public Card save(Card card){
        return cardRepository.save(card);
    }

}
