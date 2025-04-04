package telekom.com.productservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import telekom.com.productservice.DTO.ProductDTO;
import telekom.com.productservice.entity.Product;
import telekom.com.productservice.mapper.Mapper;
import telekom.com.productservice.repository.ProductRepository;
import telekom.com.productservice.service.ProductService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PurchaseControllerTests {

    @Mock
    private ProductService productService;
    @Mock
    private Mapper<Product, ProductDTO> mapper;
    @InjectMocks
    private PurchaseController purchaseController;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(123456789);
        product.setName("Product Name");
    }

    @Test
    public void successfulPurchaseNonUser() {
        productService.save(product);

        Optional<Product> productOptional = Optional.of(product);
        when(productService.findById(product.getId())).thenReturn(productOptional);

        ResponseEntity<ProductDTO> response = purchaseController.purchaseNonUser(product.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProductDTO productDTO = mapper.mapTo(product);
        assertEquals(productDTO, response.getBody());
    }

    @Test
    public void failedPurchaseNonUser() {
        Optional<Product> productOptional = Optional.empty();
        when(productService.findById(-1)).thenReturn(productOptional);

        ResponseEntity<ProductDTO> response = purchaseController.purchaseNonUser(-1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    //todo: tests for users (I think I don't have db for them as of now)
    @Test
    public void successfulPurchaseUser() {

    }
    @Test
    public void failedPurchaseUser() {

    }

}
