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

    @Test
    @DataSet(value = "testSaveCard.xml")
    @ExpectedDataSet(value = "testSaveCard-expected.xml")
    public void testSaveCard(){
        Card card = new Card();
        card.setCardNumber("123");
        card.setCvv("321");
        card.setEndDate(Instant.ofEpochSecond(0));
        User user = new User();
        user.setLogin("login");
        user.setPasswordHash("pwd");
        card.setOwner(user);
        Card savedCard = cardJdbcRepository.save(card);

        System.out.println(savedCard+"_______________________________________________________________________");

        assertNotNull(savedCard.getCardNumber());
    }

    @Test
    @DataSet(value = "testFindCardsByUser.xml")
    @ExpectedDataSet(value = "testFindCardsByUser-expected.xml")
    public void testFindCardsByUser(){
        User user = new User();
        user.setLogin("login");
        user.setPasswordHash("pwd");
        Card card = new Card();
        card.setCardNumber("123");
        card.setCvv("123");
        card.setOwner(user);
        card.setEndDate(Instant.ofEpochSecond(0));

        Card card1 = new Card();
        card1.setCardNumber("321");
        card1.setCvv("321");
        card1.setOwner(user);
        card1.setEndDate(Instant.ofEpochSecond(0));



        List<Card> cards = cardJdbcRepository.findCardsByUser(user);

        assertNotNull(cards);


    }

    @Test
    @DataSet(value = "testUpdateCard.xml")
    @ExpectedDataSet(value = "testUpdateCard-expected.xml")
    public void testUpdateCard(){
        Card card = new Card();
        card.setCardNumber("123");
        card.setCvv("321");
        card.setEndDate(Instant.ofEpochSecond(0));


        Card savedCard = cardJdbcRepository.save(card);

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
