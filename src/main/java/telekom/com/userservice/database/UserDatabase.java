package telekom.com.userservice.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import telekom.com.userservice.model.User;

import java.util.Optional;

public interface UserDatabase extends MongoRepository<User, String> {

    Optional<User> findByName(String name);

    User save(User user);
}
