package telekom.com.fidelityservice.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import telekom.com.fidelityservice.model.User;

import java.util.Optional;

public interface FidelityDatabase extends MongoRepository<User, String> {

    Optional<User> findById(int id);
}
