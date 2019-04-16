package software.jevera;


import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



import software.jevera.domain.BankAccount;
import software.jevera.domain.User;
import software.jevera.service.bankaccount.BankAccountService;
import software.jevera.service.bankaccount.BankAccountStateEnum;
import software.jevera.service.bankaccount.StateMachine;

//добавить рестор бай банк рестор бай юзер!
//начислить деньги, метод трансфер(с какой карты на какую), снять деньги
@SpringBootApplication
public class OnlineBanking {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OnlineBanking.class, args);
        BankAccountService bankAccountService = context.getBean(BankAccountService.class);

        User user = new User();

        BankAccount bankAccount = bankAccountService.createBankAccount(new BankAccount(), user);


        //bankAccountService.blockByBank(bankAccount.getId());
    }

}
