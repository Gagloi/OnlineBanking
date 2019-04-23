package software.jevera.dao.inmemory;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "application.datamode", havingValue = "inmemory")
public class InMemoryRepositoryConfiguration {

    @Bean
    public BankAccountInMemoryRepository bankAccountInMemoryRepository(){
        return new BankAccountInMemoryRepository();
    }

    @Bean
    public CardInMemoryRepository cardInMemoryRepository(){
        return new CardInMemoryRepository();
    }

    @Bean
    public UserInMemoryRepository userInMemoryRepository(){
        return new UserInMemoryRepository();
    }

}
