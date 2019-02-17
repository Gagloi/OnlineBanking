package main.java.software.jevera.service;

import main.java.software.jevera.dao.UserRepository;
import main.java.software.jevera.domain.User;
import main.java.software.jevera.exceptions.UserAlreadyExist;

import java.math.BigInteger;
import java.security.MessageDigest;

import static java.nio.charset.StandardCharsets.UTF_8;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        if (userRepository.isUserAlreadyExist(user.getLogin())) {
            throw new UserAlreadyExist(user.getLogin());
        }
        return userRepository.save(user);

    }

    private static String encryptPassword(String password) {

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes(UTF_8));
            return new BigInteger(1, crypt.digest()).toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
