package software.jevera.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.service.CardService;
import software.jevera.service.bankaccount.BankAccountService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/cards")
@RestController
public class CardController {

    private final CardService cardService;
    private final HttpSession httpSession;

    @GetMapping
    public List<Card> getCards(){
        return cardService.getCardByUser(getUser());
    }



    private User getUser(){
        return (User)httpSession.getAttribute("user");
    }

}
