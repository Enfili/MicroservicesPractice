package telekom.com.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import telekom.com.productservice.DTO.ProductDTO;
import telekom.com.productservice.entity.Product;
import telekom.com.productservice.mapper.Mapper;
import telekom.com.productservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private Mapper<Product, ProductDTO> mapper;

    @PostMapping(path = "/addProduct")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = mapper.mapFrom(productDTO);
        Product savedProduct = productService.save(product);
        ProductDTO savedProductDTO = mapper.mapTo(savedProduct);

        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/deleteProductById")
    public ResponseEntity deleteProductById(@RequestParam int id) {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/deleteDuplicates")
    public ResponseEntity<ProductDTO> deleteDuplicates(@RequestParam String name) {
        List<Product> products = productService.findAllByName(name);

        if (products.size() > 1) {
            for (int i = 1; i < products.size(); i++) {
                productService.delete(products.get(i));
            }
        }

        return new ResponseEntity<>(mapper.mapTo(products.get(0)), HttpStatus.OK);
    }
}
