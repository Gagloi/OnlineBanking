package software.jevera.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.domain.dto.BankAccMapper;
import software.jevera.domain.dto.BankAccountDto;
import software.jevera.domain.dto.CardDto;
import software.jevera.domain.dto.CardMapper;
import software.jevera.service.bankaccount.BankAccountService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/bankaccounts")
@RestController
public class BankAccountController {

    private final BankAccMapper bankAccMapper;
    private final CardMapper cardMapper;
    private final BankAccountService bankAccountService;
    private final HttpSession httpSession;

    @PostMapping
    public BankAccount create(BankAccountDto bankAccount){
        return bankAccountService.createBankAccount(bankAccMapper.toBankaccount(bankAccount), getUser());
    }

    @GetMapping("/own")
    public BankAccount ownBankAccount(){
        return bankAccountService.findByUser(getUser());
    }

    @PostMapping("/bankAccount/{id}/blockByBank")
    public void blockByBank(@PathVariable("id") Long id) {
        bankAccountService.blockByBank(id);
    }

    @PostMapping("/bankAccount")
    public void addCard(CardDto card) {
        bankAccountService.addCard(getUser(), cardMapper.toCard(card));
    }

    private User getUser(){
        return (User) httpSession.getAttribute("user");
    }

}
