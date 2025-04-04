package telekom.com.userservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private double moneySpent;
}
