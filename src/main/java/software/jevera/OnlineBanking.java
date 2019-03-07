package software.jevera;

import software.jevera.configuration.AppFactory;
import software.jevera.domain.BankAccount;
import software.jevera.domain.User;

//добавить рестор бай банк рестор бай юзер!
//начислить деньги, метод трансфер(с какой карты на какую), снять деньги
public class OnlineBanking {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        BankAccount bankAccount = new BankAccount();
        User user = new User();
        AppFactory.bankAccountService.createBankAccount(bankAccount, user);

        AppFactory.bankAccountService.chargeBalance(AppFactory.bankAccountService.findByUser(user).getId(), 10);
        System.out.println(AppFactory.bankAccountService.findByUser(user).getBalance());

    }
}
