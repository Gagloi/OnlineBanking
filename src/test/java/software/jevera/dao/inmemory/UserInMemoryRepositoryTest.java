package software.jevera.dao.inmemory;

import org.junit.Test;
import software.jevera.domain.User;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

public class UserInMemoryRepositoryTest {

    private UserInMemoryRepository repository = new UserInMemoryRepository();


    @Test
    public void isUserAlreadyExist() {
        List<User> users = asList(
                new User("1", "p1"),
                new User("2", "p2")
        );
        users.forEach(repository::save);
        assertTrue(repository.isUserAlreadyExist("1"));
        assertFalse(repository.isUserAlreadyExist("5"));
    }

    @Test
    public void save() {
        User user = new User();
        user.setLogin("Glek");
        repository.save(user);
        assertEquals(user, repository.findUserByLogin("Glek").get());
    }

    @Test
    public void findUserByLogin() {
        List<User> users = asList(
                new User("1", "p1"),
                new User("2", "p2")
        );
        users.forEach(repository::save);

        assertEquals(Optional.of(new User("1", "p1")), repository.findUserByLogin("p1"));
        assertEquals(Optional.empty(), repository.findUserByLogin("5"));
    }
}