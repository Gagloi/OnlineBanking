package software.jevera.web;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
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

    }

    @Test
    public void blockByBank() {
    }

    @Test
    @SneakyThrows
    public void addCard() {

        Map<String, Object> sessionattr = new HashMap<>();
        sessionattr.put("cardDto", new CardDto());

        mockMvc.perform(post("/api/bankaccounts/addcard").sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @SneakyThrows
    public void topUpTheBalance() {

        Map<String, Object> sessionattr = new HashMap<>();
        BankAccount bankAccount = new BankAccount();
        bankAccountService.createBankAccount(bankAccount, new User());
        bankAccount.setId(2L);
        sessionattr.put("id", bankAccount.getId());
        sessionattr.put("amount", 10);

        System.out.println(sessionattr);
        mockMvc.perform(post("/api/bankaccounts/topup")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());

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

    }

    @Test
    @SneakyThrows
    public void doTransition() {

        Map<String, Object> sessionattr = new HashMap<>();

        Card card = new Card("123", "eqw", Instant.now());

        sessionattr.put("card", card);
        sessionattr.put("amount", 10);

        System.out.println(sessionattr);
        mockMvc.perform(post("/api/bankaccounts/dotransition")
                .sessionAttrs(sessionattr))
                .andDo(print())
                .andExpect(status().isOk());

    }
}