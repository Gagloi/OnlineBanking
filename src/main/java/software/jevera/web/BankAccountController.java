package software.jevera.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.domain.dto.*;
import software.jevera.service.bankaccount.BankAccountService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/bankaccounts")
@RestController
public class BankAccountController {

    private final BankAccMapper bankAccMapper;
    private final CardMapper cardMapper;
    private final BankAccountService bankAccountService;
    private final HttpSession httpSession;

    @PostMapping
    public BankAccount create(@RequestBody BankAccountDto bankAccount){
        return bankAccountService.createBankAccount(bankAccMapper.toBankaccount(bankAccount), getUser());
    }

    @GetMapping("/own")
    public BankAccount ownBankAccount(){
        return bankAccountService.findByUser(getUser());
    }

    @PostMapping("/blockbybank/{id}")
    public void blockByBank(@PathVariable("id") Long id) {
        bankAccountService.blockByBank(id);
    }

    @PostMapping("/blockbyuser")
    public void blockByUser() {
        bankAccountService.blockByUser(getUser());
    }

    @PostMapping("/restorebyuser")
    public void restoreByUser() {
        bankAccountService.restoreByUser(getUser());
    }

    @PostMapping("/activate/{id}")
    public void activate(@PathVariable("id") Long id) {
        bankAccountService.activate(id);
    }

    @PostMapping("/restorebybank/{id}")
    public void restoreByBank(@PathVariable("id") Long id) {
        bankAccountService.restoreByBank(id);
    }


    @PostMapping("/addcard")
    public void addCard(@RequestBody CardDto card) {
        bankAccountService.addCard(getUser(), cardMapper.toCard(card));
    }

    @PostMapping("/topup")
    public void topUpTheBalance(@RequestBody TopUpDto topUpDto) {
        bankAccountService.topUpTheBalance(topUpDto, getUser());
    }

    @PostMapping("/getmoney")
    public void getMoney(String cvv, String cardNumber, Integer amount) {
        bankAccountService.getMoney(cvv, cardNumber, getUser(), amount);
    }

    @PostMapping("/dotransition")
    public void doTransition(@RequestParam(value = "card", required = false) CardDto card,Integer amount) {
        bankAccountService.doTransition(getUser(), cardMapper.toCard(card), amount);
    }



    private User getUser(){
        return (User) httpSession.getAttribute("user");
    }

}
