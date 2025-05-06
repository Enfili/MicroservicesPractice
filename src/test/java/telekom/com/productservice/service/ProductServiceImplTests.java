package telekom.com.productservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import telekom.com.productservice.entity.Product;
import telekom.com.productservice.repository.ProductRepository;
import telekom.com.productservice.service.impl.ProductServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTests {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1);
        product.setName("test");
        product.setDescription("description");
        product.setPrice(1.0);
    }

    @Test
    void saveProductShouldReturnProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertEquals(savedProduct, product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void deleteShouldInvokeRepositoryDelete() {
        productService.delete(product);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteByIdShouldInvokeRepositoryDeleteById() {
        productService.deleteById(1);

        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void findAllByNameReturnListOfProducts() {
        List<Product> productList = Arrays.asList(product);
        when(productService.findAllByName("test")).thenReturn(productList);

        List<Product> foundProducts = productService.findAllByName("test");

        assertEquals(foundProducts, productList);
        assertEquals(foundProducts.size(), 1);
        verify(productRepository, times(1)).findAllByName("test");
    }

    @Test
    void findByIdShouldReturnOptionalProduct() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findById(product.getId());

        assertEquals(foundProduct.get().getId(), product.getId());
        assertEquals(foundProduct.get().getName(), product.getName());
        assertEquals(foundProduct.get().getDescription(), product.getDescription());
        assertEquals(foundProduct.get().getPrice(), product.getPrice());
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void findByIdShouldReturnEmptyWhenProductNotFound() {
        when(productRepository.findById(product.getId() + 1)).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.findById(product.getId() + 1);

        assertEquals(Optional.empty(), foundProduct);
        verify(productRepository, times(1)).findById(product.getId() + 1);
    }
}
