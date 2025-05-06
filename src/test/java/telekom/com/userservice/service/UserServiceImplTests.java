package telekom.com.userservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import telekom.com.userservice.database.UserDatabase;
import telekom.com.userservice.model.User;
import telekom.com.userservice.service.implementation.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserDatabase userDatabase;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setName("name");
        user.setPassword("password");
        user.setEmail("email");
        user.setMoneySpent(11);
    }

    @Test
    void registerSuccessfullyNewUser() {
        when(userDatabase.save(user)).thenReturn(user);

        Optional<User> registeredUser = userService.register(user);

        assertTrue(registeredUser.isPresent());
        assertEquals(user, registeredUser.get());
        verify(userDatabase, times(1)).save(user);
    }

    @Test
    void getUserShouldReturnUser() {
        when(userDatabase.findByName(user.getName())).thenReturn(Optional.of(user));

        Optional<User> registeredUser = userService.getUser(user.getName());

        assertTrue(registeredUser.isPresent());
        assertEquals(user, registeredUser.get());
        verify(userDatabase, times(1)).findByName(user.getName());
    }

    @Test
    void getUserShouldReturnEmptyWhenUserNotFound() {
        when(userDatabase.findByName(user.getName() + "blabla")).thenReturn(Optional.empty());

        Optional<User> registeredUser = userService.getUser(user.getName() + "blabla");

        assertTrue(registeredUser.isEmpty());
        verify(userDatabase, times(1)).findByName(user.getName() + "blabla");
    }
}
