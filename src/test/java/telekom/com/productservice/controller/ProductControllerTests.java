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
import telekom.com.productservice.service.ProductService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {

    @Mock
    private ProductService productService;
    @Mock
    private Mapper<Product, ProductDTO> mapper;
    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();
        productDTO.setId(123456789);
        productDTO.setName("test product dto");

        product = new Product();
        product.setId(123456789);
        product.setName("test product");
    }

    @Test
    public void testAddProduct() {
        when(mapper.mapFrom(productDTO)).thenReturn(product);
        when(mapper.mapTo(product)).thenReturn(productDTO);
        when(productService.save(product)).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.addProduct(productDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productDTO, response.getBody());
        verify(productService, times(1)).save(product);
    }

    @Test
    public void testDeleteById() {
        productController.addProduct(productDTO);

        ResponseEntity<ProductDTO> response = productController.deleteProductById(productDTO.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteById(productDTO.getId());
    }

    @Test
    public void testDeleteDuplicates() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("test product");

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("test product");

        Product product3 = new Product();
        product3.setId(3);
        product3.setName("test product");

        productService.save(product1);
        productService.save(product2);
        productService.save(product3);

        List<Product> productList = Arrays.asList(product1, product2, product3);
        when(productService.findAllByName("test product")).thenReturn(productList);

        productController.addProduct(mapper.mapTo(product1));
        productController.addProduct(mapper.mapTo(product2));
        productController.addProduct(mapper.mapTo(product3));

        ResponseEntity<ProductDTO> response = productController.deleteDuplicates("test product");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mapper.mapTo(product1), response.getBody());
    }
}
