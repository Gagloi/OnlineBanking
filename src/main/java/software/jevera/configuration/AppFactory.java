package software.jevera.configuration;

import software.jevera.dao.inmemory.BankAccountInMemoryRepository;
import software.jevera.dao.inmemory.CardInMemoryRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;
import software.jevera.service.CardService;
import software.jevera.service.UserService;
import software.jevera.service.bankaccount.*;

import static java.util.Arrays.asList;

//public class AppFactory {
//
//    public static final UserService userService;
//    public static final CardService cardService;
//    public static final BankAccountService bankAccountService;
//
//    static{
//        userService = new UserService(new UserInMemoryRepository());
//        cardService = new CardService(new CardInMemoryRepository());
//        StateMachine stateMachine = new StateMachine(asList(
//                new Created(),
//                new Confirmed(),
//                new Rejected(),
//                new Restored(),
//                new BlockedByBank(),
//                new BlockedByUser()
//        ));
//        bankAccountService = new BankAccountService(new BankAccountInMemoryRepository(), stateMachine);
//    }
//}
