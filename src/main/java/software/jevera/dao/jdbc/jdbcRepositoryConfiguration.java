package software.jevera.dao.jdbc;


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
@ConditionalOnProperty(value = "application.datamode", havingValue = "jdbc")
public class jdbcRepositoryConfiguration {

    @Bean
    public BankAccountRepository bankAccountInMemoryRepository(){
        return new BankAccountInMemoryRepository();
    }

    @Bean
    public CardRepository cardInMemoryRepository(ConnectionManager connectionManager, UserRepository userRepository){
        return new CardJdbcRepository(connectionManager, userRepository);
    }

    @Bean
    public UserRepository userInMemoryRepository(){
        return new UserInMemoryRepository();
    }

}
