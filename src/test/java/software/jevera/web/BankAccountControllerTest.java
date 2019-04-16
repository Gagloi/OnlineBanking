package software.jevera.web;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.domain.dto.BankAccMapper;
import software.jevera.domain.dto.CardDto;
import software.jevera.domain.dto.CardMapper;
import software.jevera.service.bankaccount.BankAccountService;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BankAccountController.class)
@ContextConfiguration(classes = {BankAccountController.class,
        BankAccountControllerTest.BankAccountTestConfiguration.class})
public class BankAccountControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankAccountController bankAccountController;

    @MockBean
    private BankAccountService bankAccountService;

    @MockBean
    private CardMapper cardMapper;

    @Configuration
    public static class BankAccountTestConfiguration{
        @Bean
        public BankAccMapper bankAccMapper(){
            return Mappers.getMapper(BankAccMapper.class);
        }

        @Bean
        public CardMapper cardMapper(){
            return Mappers.getMapper(CardMapper.class);
        }

    }

    @Test
    @SneakyThrows
    public void create() {

        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("user", new User());

        mockMvc.perform(post("/api/bankaccounts").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @SneakyThrows
    public void ownBankAccount() {

        Map<String, Object> sessionattr = new HashMap<>();
        User user = new User();
        sessionattr.put("user", user);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(10);
        bankAccountService.createBankAccount(bankAccount, user);

        mockMvc.perform(get("/api/bankaccounts/own").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(bankAccountService).createBankAccount(bankAccount, user);

    }

    @Test
    public void blockByBank() {
    }

    @Test
    @SneakyThrows
    public void addCard() {

        Map<String, Object> sessionattr = new HashMap<>();
        CardDto cardDto = new CardDto();
        cardDto.setCardNumber("1234123412341234");
        User user = new User();
        sessionattr.put("card", cardDto);
        sessionattr.put("user", user);

        mockMvc.perform(post("/api/bankaccounts/addcard").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bankAccountService).addCard(user, cardMapper.toCard(cardDto));

    }

    @Test
    @SneakyThrows
    public void topUpTheBalance() {

        Map<String, Object> sessionattr = new HashMap<>();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(2L);
        User user = new User();
        bankAccount.setOwner(user);

        sessionattr.put("id", bankAccount.getId());
        sessionattr.put("amount", 10);

        System.out.println(bankAccount);
        mockMvc.perform(post("/api/bankaccounts/topup").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bankAccountService).topUpTheBalance(bankAccount.getId(), bankAccount.getBalance());

    }

    @Test
    @SneakyThrows
    public void getMoney() {

        Map<String, Object> sessionattr = new HashMap<>();

        Card card = new Card("123", "eqw", Instant.now());


        sessionattr.put("cardNumber", card.getCardNumber());
        sessionattr.put("cvv", card.getCvv());
        sessionattr.put("amount", 10);

        System.out.println(sessionattr);
        mockMvc.perform(post("/api/bankaccounts/getmoney")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bankAccountService).getMoney(card.getCvv(), card.getCardNumber(), new User(), 10);
    }

    @Test
    @SneakyThrows
    public void doTransition() {

        Map<String, Object> sessionattr = new HashMap<>();

        Card card = new Card("123", "eqw", Instant.now());

        CardDto cardDto = new CardDto();
        cardDto.setCardNumber("1234123412341234");
        sessionattr.put("card", cardDto);
        sessionattr.put("amount", 10);

        System.out.println(sessionattr);
        mockMvc.perform(post("/api/bankaccounts/dotransition")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());
        Mockito.verify(bankAccountService).doTransition(new User(), cardMapper.toCard(cardDto), 10);

    }
}