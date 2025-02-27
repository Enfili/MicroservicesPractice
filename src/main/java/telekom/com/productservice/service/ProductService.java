package telekom.com.productservice.service;

import telekom.com.productservice.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product product);

    void delete(Product product);

    void deleteById(int id);

    List<Product> findAllByName(String name);

    Optional<Product> findById(int id);


}
