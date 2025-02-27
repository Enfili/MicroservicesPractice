package telekom.com.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telekom.com.productservice.DTO.ProductDTO;
import telekom.com.productservice.entity.Product;
import telekom.com.productservice.mapper.Mapper;
import telekom.com.productservice.service.ProductService;

import java.util.Optional;


@RestController
public class PurchaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private Mapper<Product, ProductDTO> mapper;

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

    @GetMapping(path = "/purchase/{user}")
    public ResponseEntity<ProductDTO> purchaseUser(@PathVariable String user, @RequestParam int productId) {
        if (user.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Product> product = productService.findById(productId);

        if (product.isPresent()) {
            Product foundProduct = product.get();
            ProductDTO productDTO = mapper.mapTo(foundProduct);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
