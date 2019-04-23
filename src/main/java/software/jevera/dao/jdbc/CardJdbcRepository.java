package software.jevera.dao.jdbc;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import software.jevera.dao.CardRepository;
import software.jevera.dao.UserRepository;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CardJdbcRepository implements CardRepository {

    private final ConnectionManager connectionManager;

    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public Card save(Card card) {
        try (Connection connection = connectionManager.createConnection()) {

            if (card.getCardNumber() == null) {
                try (Statement statement = connection.createStatement()) {

                    statement.executeUpdate(String.format("INSERT into card " +
                                    "(card_number, cvv, owner_login, end_date) " +
                                    "values ('%s', '%s', '%s', '%s')",
                            card.getCardNumber(),
                            card.getCvv(),
                            card.getOwner().getLogin(),
                            card.getEndDate().toString()));
                }

            }else {

                String sql = "UPDATE card SET cvv=? WHERE card_number=?";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setString(1, card.getCvv());
                    preparedStatement.setString(2, card.getCardNumber());
                    preparedStatement.executeUpdate();
                }

            }
        }

        return card;
    }

    @Override
    public List<Card> findCardsByUser(User user) {

        List<Card> cards = new ArrayList<>();

        try (Connection connection = connectionManager.createConnection()){
            String sql = "SELECT * FROM card INNER JOIN user u on card.owner_login = u.login WHERE owner_login = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql)){

                statement.setString(1, user.getLogin());
                try(ResultSet set = statement.executeQuery()){
                    while (set.next()){
                        Card card = new Card();
                        card.setOwner(set.getObject("owner", User.class));
                    }
                }
            }

        }catch (SQLException e){
            log.error("Error: ", e);
        }
        return null;
    }

    @Override
    public List<Card> findAll() {
        return null;
    }
}
