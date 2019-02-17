package main.java.software.jevera.dao;

import main.java.software.jevera.domain.User;

import java.util.Optional;

public interface UserRepository {

    boolean isUserAlreadyExist(String login);

    User save(User user);

    Optional<User> findUserByLogin(String login);


}
