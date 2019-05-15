package software.jevera.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.jevera.dao.UserRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;
import software.jevera.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository {

    private final ConnectionManager connectionManager;

    @Override
    @SneakyThrows
    public boolean isUserAlreadyExist(String login) {

        try(Connection connection = connectionManager.createConnection()) {
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM owner WHERE login=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, login);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        String userLogin = rs.getString("login");
                        if(userLogin.equals(login)){
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Is user already exist error! {}", e);
                connection.rollback();
                throw e;
            }
        }
        return false;
    }

    @Override
    @SneakyThrows
    public User save(User user) {
        try(Connection connection = connectionManager.createConnection()){
            connection.setAutoCommit(false);
            try {
                if (!isUserAlreadyExist(user.getLogin())) {
                    String sql = "INSERT into owner (login, password_hash) values (?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, user.getLogin());
                        statement.setString(2, user.getPasswordHash());
                        statement.executeUpdate();
                    }

                } else {

                    String sql = "UPDATE owner SET password_hash=? WHERE login=?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, user.getLogin());
                        preparedStatement.setString(2, user.getPasswordHash());
                        preparedStatement.executeUpdate();
                    }

                }
                connection.commit();
            } catch (Exception e) {
                log.error("Error saving user {}", e);
                connection.rollback();
                throw e;
            }

        }
        return user;
    }

    @Override
    @SneakyThrows
    public Optional<User> findUserByLogin(String login) {
        User user = new User();
        try(Connection connection = connectionManager.createConnection()){
            connection.setAutoCommit(false);
            try {
                String sql = "SELECT * from owner WHERE login=?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, login);
                    try(ResultSet set = statement.executeQuery()){
                        while (set.next()) {
                            String userLogin = set.getString("login");
                            String passwordHash = set.getString("password_hash");
                            user = new User(passwordHash, userLogin);
                        }
                    }
                }
                connection.commit();
            } catch (Exception e) {
                log.error("Error saving user {}", e);
                connection.rollback();
                throw e;
            }

        }
        return Optional.of(user);
    }
}
