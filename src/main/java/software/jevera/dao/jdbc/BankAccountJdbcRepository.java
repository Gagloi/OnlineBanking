package software.jevera.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.jevera.dao.BankAccountRepository;
import software.jevera.dao.CardRepository;
import software.jevera.dao.UserRepository;
import software.jevera.domain.BankAccount;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class BankAccountJdbcRepository implements BankAccountRepository {

    private final ConnectionManager connectionManager;
    private final CardRepository cardRepository;

    @Override
    @SneakyThrows
    public BankAccount save(BankAccount bankAccount) {
        try (Connection connection = connectionManager.createConnection()) {
            connection.setAutoCommit(false);
            try {
                if(bankAccount.getId() == null) {
                    String sql = "INSERT into bank_account (id, creation_date, balance, current_state, owner_login) values (? , ? , ? , ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setLong(1, preparedStatement.RETURN_GENERATED_KEYS);
                        preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
                        preparedStatement.setInt(3, bankAccount.getBalance());
                        preparedStatement.setString(4, bankAccount.getCurrentState().toString());
                        preparedStatement.setString(5, bankAccount.getOwner().getLogin());
                        preparedStatement.executeUpdate();
                    }
                }else{
                    String sql = "UPDATE bank_account SET balance=?, current_state=? WHERE id=?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                        preparedStatement.setInt(1, bankAccount.getBalance());
                        preparedStatement.setString(2, bankAccount.getCurrentState().toString());
                        preparedStatement.setLong(3, bankAccount.getId());
                        preparedStatement.executeUpdate();
                    }
                }
                connection.commit();
            } catch (Exception e) {
                log.error("Error saving bank account {}", e);
                connection.rollback();
                throw e;
            }
        }
        log.info("Saved bank account {}", bankAccount);
        return bankAccount;
    }

    @Override
    @SneakyThrows
    public Optional<BankAccount> findByUser(User user) {

        BankAccount bankAccount = new BankAccount();
        try (Connection connection = connectionManager.createConnection()) {
            connection.setAutoCommit(false);
            try {
                String sql = "SELECT * FROM bank_account WHERE owner_login=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, user.getLogin());
                    try(ResultSet set = preparedStatement.executeQuery()){
                        while (set.next()){
                            bankAccount.setOwner(new User(set.getString("owner_login")));
                            bankAccount.setId(set.getLong("id"));
                            bankAccount.setBalance(set.getInt("balance"));
                            //bankAccount.setCurrentState(set.getString("current_state"));
                        }
                    }

                }

                connection.commit();
            } catch (Exception e) {
                log.error("Error find by user {}", e);
                connection.rollback();
                throw e;
            }
        }
        if(bankAccount.getId() != null) {
            bankAccount.setCards(cardRepository.findCardsByUser(bankAccount.getOwner()));
        }
        log.info("Bank account {}", bankAccount);
        return Optional.of(bankAccount);
    }

    @Override
    @SneakyThrows
    public Optional<BankAccount> findById(Long id) {
        BankAccount bankAccount = new BankAccount();
        try (Connection connection = connectionManager.createConnection()) {
            connection.setAutoCommit(false);
            try {
                String sql = "SELECT * FROM bank_account WHERE id=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setLong(1, id);
                    try(ResultSet set = preparedStatement.executeQuery()){
                        while (set.next()){
                            bankAccount.setOwner(new User(set.getString("owner_login")));
                            bankAccount.setId(set.getLong("id"));
                            bankAccount.setBalance(set.getInt("balance"));
                            //bankAccount.setCurrentState(set.getString("current_state"));
                        }
                    }

                }

                connection.commit();
            } catch (Exception e) {
                log.error("Error find by user {}", e);
                connection.rollback();
                throw e;
            }
        }
        log.info("Bank account {}", bankAccount);
        return Optional.of(bankAccount);
    }

    @Override
    @SneakyThrows
    public void delete(Long delete) {
        try (Connection connection = connectionManager.createConnection()) {
            connection.setAutoCommit(false);
            try {
                String sql = "DELETE FROM bank_account WHERE id=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setLong(1, delete);
                    preparedStatement.executeUpdate();
                }
                connection.commit();
            } catch (Exception e) {
                log.error("Error deletion of bank account {}", e);
                connection.rollback();
                throw e;
            }
        }
    }
}
