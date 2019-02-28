package software.jevera.dao;

import software.jevera.domain.User;

import java.util.Optional;

public interface UserRepository {

    boolean isUserAlreadyExist(String login);

    User save(User user);

    Optional<User> findUserByLogin(String login);


}
