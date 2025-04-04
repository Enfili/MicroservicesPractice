package telekom.com.fidelityservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "fidelity")
public class User {
    private int id;
    private String name;
    private double moneySpent;
}

