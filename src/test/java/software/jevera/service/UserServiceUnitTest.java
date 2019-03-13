package software.jevera.service;

import java.time.Instant;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import software.jevera.dao.CardRepository;
import software.jevera.dao.UserRepository;
import software.jevera.domain.Card;
import software.jevera.domain.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceUnitTest
{
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void before(){
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void registerUser()
    {
        User user = new User("login", "passwd");
        when(userRepository.isUserAlreadyExist("passwd")).thenReturn(false);
        userService.registerUser(user);
        verify(userRepository).isUserAlreadyExist("passwd");
    }

    @Test
    public void findUserByLogin()
    {
        User user = new User("login", "login");
        when(userRepository.findUserByLogin("login")).thenReturn(Optional.of(user));
        userService.findUserByLogin("login");
        verify(userRepository).findUserByLogin("login");

    }
}
