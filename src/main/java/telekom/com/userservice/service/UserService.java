package telekom.com.userservice.service;

import telekom.com.userservice.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String name);

    Optional<User> register(User user);

}
