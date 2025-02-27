package telekom.com.productservice.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private double price;

}
