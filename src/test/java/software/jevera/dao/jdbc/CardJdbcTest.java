package software.jevera.dao.jdbc;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import software.jevera.dao.UserRepository;
import software.jevera.domain.Card;
import software.jevera.domain.User;
import software.jevera.service.UserServiceUnitTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Instant;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

@RunWith(JUnit4.class)
@DBUnit(url = CardJdbcTest.DB_URL, driver = "org.h2.Driver", user = "postgres")
@Slf4j
public class CardJdbcTest {

    public static final String DB_URL = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;MODE=PostgreSQL";
    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    private CardJdbcRepository cardJdbcRepository;

    @BeforeClass
    @SneakyThrows
    public static void beforeClass() {
        Connection connection = DriverManager.getConnection(DB_URL, "postgres", null);
        Database database = DatabaseFactory
                .getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new liquibase.Liquibase("db/changelog/db.changelog-master.yaml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @Before
    public void before() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl(DB_URL);
        properties.setUsername("postgres");
        ConnectionManager connectionManager = new ConnectionManager(properties);

        cardJdbcRepository = new CardJdbcRepository(connectionManager, new UserJdbcRepository(connectionManager));
    }

    private Card createCard(String cardNumber, String cvv, Instant createDate, User owner){
        Card card = new Card(owner, cardNumber, cvv, createDate);
        log.info("Creating card with values {}", card);
        return card;
    }
    @Test
    @DataSet(value = "testSaveCard.xml")
    @ExpectedDataSet(value = "testSaveCard-expected.xml")
    public void testSaveCard(){
        Card card = createCard("123", "123", Instant.ofEpochSecond(0), new User("pwd", "login"));
//        Card card = new Card();
//        card.setCardNumber("123");
//        card.setCvv("123");
//        card.setEndDate(Instant.ofEpochSecond(0));
//        User user = new User();
//        user.setLogin("login");
//        user.setPasswordHash("pwd");
//        card.setOwner(user);
        Card savedCard = cardJdbcRepository.save(card);

        assertNotNull(savedCard.getCardNumber());
    }

    @Test
    @DataSet(value = "testFindCardsByUser.xml")
    @ExpectedDataSet(value = "testFindCardsByUser-expected.xml")
    public void testFindCardsByUser(){
        User user = new User();
        user.setLogin("login");
        user.setPasswordHash("pwd");
        Card card = createCard("123", "123", Instant.ofEpochSecond(0), user);
        Card card1 = createCard("321", "321", Instant.ofEpochSecond(0), user);

        List<Card> cards = cardJdbcRepository.findCardsByUser(user);
        log.info("CARD 0 {}", cards.get(0));
        log.info("CARD 1 {}", cards.get(1));

        assertNotNull(cards);


    }

    @Test
    @DataSet(value = "testUpdateCard.xml")
    @ExpectedDataSet(value = "testUpdateCard-expected.xml")
    public void testUpdateCard(){
        List<Card> cards = cardJdbcRepository.findCardsByUser(new User("pwd", "login"));
        log.info(" CARD {}", cards.get(0));
        Card savedCard = cardJdbcRepository.save(cards.get(0));

        assertNotNull(savedCard.getCardNumber());
    }

    @Test
    @ExpectedDataSet(value = "testUpdateCard-expected.xml")
    public void findAllCards(){
        Card card = new Card();
        card.setCardNumber("123");
        card.setCvv("321");
        card.setEndDate(Instant.ofEpochSecond(0));


        Card savedCard = cardJdbcRepository.save(card);

        assertNotNull(savedCard.getCardNumber());
    }
}
