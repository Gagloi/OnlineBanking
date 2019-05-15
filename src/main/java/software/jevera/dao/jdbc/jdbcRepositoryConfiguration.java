package software.jevera.dao.jdbc;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.jevera.dao.BankAccountRepository;
import software.jevera.dao.CardRepository;
import software.jevera.dao.UserRepository;
import software.jevera.dao.inmemory.BankAccountInMemoryRepository;
import software.jevera.dao.inmemory.CardInMemoryRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.datamode", havingValue = "jdbc")
public class jdbcRepositoryConfiguration {

    private final ConnectionManager connectionManager;

    @Bean
    public BankAccountRepository bankAccountInMemoryRepository(CardRepository cardRepository){
        return new BankAccountJdbcRepository(connectionManager, cardRepository);
    }

    @Bean
    public CardRepository cardInMemoryRepository(UserRepository userRepository){
        return new CardJdbcRepository(connectionManager, userRepository);
    }

    @Bean
    public UserRepository userInMemoryRepository(){
        return new UserJdbcRepository(connectionManager);
    }

}
