package software.jevera.dao.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.jevera.dao.UserRepository;
import software.jevera.dao.inmemory.UserInMemoryRepository;
import software.jevera.domain.User;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository {

    private final ConnectionManager connectionManager;

    @Override
    public boolean isUserAlreadyExist(String login) {
        return false;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return Optional.empty();
    }
}
