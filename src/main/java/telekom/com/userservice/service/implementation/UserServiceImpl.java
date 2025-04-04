package telekom.com.userservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telekom.com.userservice.database.UserDatabase;
import telekom.com.userservice.model.User;
import telekom.com.userservice.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDatabase userDatabase;

    @Override
    public Optional<User> register(User user) {
        return Optional.of(userDatabase.save(user));
    }

    @Override
    public Optional<User> getUser(String name) {
        return userDatabase.findByName(name);
    }

}
