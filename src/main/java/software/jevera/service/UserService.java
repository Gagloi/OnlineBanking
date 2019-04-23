package software.jevera.service;

import software.jevera.dao.UserRepository;
import software.jevera.domain.User;
import software.jevera.domain.UserDto;
import software.jevera.exceptions.BusinessException;
import software.jevera.exceptions.EncryptRuntimeException;
import software.jevera.exceptions.UncorrectGrant;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;
@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(UserDto userDto) {
        if (userRepository.isUserAlreadyExist(userDto.getLogin())) {
            throw new BusinessException("Опоздал ты с логином!");
        }

        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPasswordHash(encryptPassword(userDto.getPassword()));
        return userRepository.save(user);
    }

    private static String encryptPassword(String password) {

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes(UTF_8));
            return new BigInteger(1, crypt.digest()).toString(16);
        } catch (Exception e) {
            throw new EncryptRuntimeException(e.getMessage());
        }

    }

    public User login(UserDto userDto) {
        return userRepository.findUserByLogin(userDto.getLogin())
                .filter(user -> checkPassword(userDto, user))
                .orElseThrow(UncorrectGrant::new);
    }

    private boolean checkPassword(UserDto userDto, User user) {
        String encryptPassword = encryptPassword(userDto.getPassword());
        return encryptPassword.equals(user.getPasswordHash());
    }

    public User findUserByLogin(String login){
        return userRepository.findUserByLogin(login).orElseThrow(() -> new BusinessException("Cannot find user with login:" + login));
    }


}
