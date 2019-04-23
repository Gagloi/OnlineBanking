package software.jevera.dao.jdbc;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.jevera.dao.CardRepository;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CardJdbcRepository implements CardRepository {

    private final ConnectionManager connectionManager;

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
        try (Connection connection = connectionManager.createConnection()){
            String sql = "SELECT * FROM card WHERE owner_login =?";
            try(PreparedStatement statement = connection.prepareStatement(sql)){

                statement.setString(1, user.getLogin());
                statement.execute();

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
