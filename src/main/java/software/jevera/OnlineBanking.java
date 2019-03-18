package software.jevera;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import software.jevera.configuration.BankConfig;
import software.jevera.domain.BankAccount;
import software.jevera.domain.User;
import software.jevera.service.bankaccount.BankAccountService;

//добавить рестор бай банк рестор бай юзер!
//начислить деньги, метод трансфер(с какой карты на какую), снять деньги
@SpringBootApplication
public class OnlineBanking {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OnlineBanking.class, args);
        BankAccountService bankAccountService = context.getBean(BankAccountService.class);
        BankAccount bankAccount = bankAccountService.createBankAccount(new BankAccount(), new User());
        System.out.println(bankAccount);

    }

}
