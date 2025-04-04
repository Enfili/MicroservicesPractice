package telekom.com.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import telekom.com.productservice.DTO.ProductDTO;
import telekom.com.productservice.DTO.UserDTO;
import telekom.com.productservice.client.FidelityServiceClient;
import telekom.com.productservice.client.UserServiceClient;
import telekom.com.productservice.entity.Product;
import telekom.com.productservice.mapper.Mapper;
import telekom.com.productservice.service.ProductService;

import java.util.Optional;


@RestController
@RequestMapping("/product")
public class PurchaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private Mapper<Product, ProductDTO> mapper;

    private final UserServiceClient userServiceClient;
    private final FidelityServiceClient fidelityServiceClient;

    public PurchaseController(UserServiceClient userServiceClient, FidelityServiceClient fidelityServiceClient) {
        this.userServiceClient = userServiceClient;
        this.fidelityServiceClient = fidelityServiceClient;
    }

    @GetMapping(path = "/test")
    public ResponseEntity<UserDTO> getUser(@RequestParam String name) {
        return userServiceClient.getUser(name);
    }

    @GetMapping(path = "/purchase")
    public ResponseEntity<ProductDTO> purchaseNonUser(@RequestParam int productId) {
        Optional<Product> product = productService.findById(productId);

        if (product.isPresent()) {
            Product foundProduct = product.get();
            ProductDTO productDTO = mapper.mapTo(foundProduct);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/purchase/{name}")
    public ResponseEntity<ProductDTO> purchaseUser(@PathVariable String name, @RequestParam int productId) {
        if (name.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ResponseEntity<UserDTO> foundUser = userServiceClient.getUser(name);

        if (foundUser == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // would be better if you could write that user not found

        Optional<Product> product = productService.findById(productId);

        if (product.isPresent()) {
            Product foundProduct = product.get();
            ProductDTO productDTO = mapper.mapTo(foundProduct);

            fidelityServiceClient.updateSpentMoney(foundUser.getBody().getId(), foundProduct.getPrice());

            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // here is product not found, before you have user not found
        }
    }
}
