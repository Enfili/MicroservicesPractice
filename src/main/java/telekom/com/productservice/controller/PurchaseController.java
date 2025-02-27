package telekom.com.productservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telekom.com.productservice.entity.Product;


@RestController
public class PurchaseController {

    @GetMapping(path = "/purchase")
    public ResponseEntity<Product> purchaseNonUser(@RequestParam int productId) {
        Product product = new Product();
        product.setDescription("This is a product");
        product.setName("Product Name");
        product.setPrice(22.3);
        product.setId(productId);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(path = "/purchase/{user}")
    public ResponseEntity<Product> purchaseUser(@PathVariable String user) {
        Product product = new Product();
        product.setDescription("This is a product for users");
        product.setName("Product Name");
        product.setPrice(22.3);
        product.setId(1);

        if (!user.isEmpty())
            return new ResponseEntity<>(product, HttpStatus.OK);

        return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
    }
}
