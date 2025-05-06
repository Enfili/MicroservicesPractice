package telekom.com.productservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import telekom.com.productservice.DTO.ProductDTO;
import telekom.com.productservice.DTO.UserDTO;
import telekom.com.productservice.client.FidelityServiceClient;
import telekom.com.productservice.client.UserServiceClient;
import telekom.com.productservice.entity.Product;
import telekom.com.productservice.mapper.Mapper;
import telekom.com.productservice.service.ProductService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseControllerTests {

    @Mock
    private ProductService productService;
    @Mock
    private Mapper<Product, ProductDTO> mapper;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private FidelityServiceClient fidelityServiceClient;
    @InjectMocks
    private PurchaseController purchaseController;

    private Product product;
    private ProductDTO productDTO;

    private final int PRODUCT_ID = 123456789;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mocks manually
        userServiceClient = mock(UserServiceClient.class);
        fidelityServiceClient = mock(FidelityServiceClient.class);
        productService = mock(ProductService.class);
        mapper = mock(Mapper.class);

        // Inject mocks into controller
        purchaseController = new PurchaseController(userServiceClient, fidelityServiceClient);
        // Inject @Autowired fields using reflection
        org.springframework.test.util.ReflectionTestUtils.setField(purchaseController, "productService", productService);
        org.springframework.test.util.ReflectionTestUtils.setField(purchaseController, "mapper", mapper);

        product = new Product();
        product.setId(PRODUCT_ID);
        product.setName("Product Name");
        product.setPrice(1);

        productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName("Product Name");
    }

    @Test
    public void successfulPurchaseNonUser() {
        productService.save(product);

        Optional<Product> productOptional = Optional.of(product);
        when(productService.findById(product.getId())).thenReturn(productOptional);
        when(mapper.mapTo(product)).thenReturn(productDTO);

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
        assertNull(response.getBody());
    }

    @Test
    public void successfulPurchaseUser() {
        String name = "Alice";

        UserDTO userDTO = new UserDTO(); userDTO.setId(101);

        productService.save(product);

        Optional<Product> productOptional = Optional.of(product);
        when(userServiceClient.getUser(name)).thenReturn(ResponseEntity.ok(userDTO));
        when(productService.findById(product.getId())).thenReturn(productOptional);
        when(mapper.mapTo(product)).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = purchaseController.purchaseUser(name, PRODUCT_ID);

        verify(fidelityServiceClient).updateSpentMoney(101, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PRODUCT_ID, response.getBody().getId());
    }

    @Test
    public void failedPurchaseUser() {
        when(userServiceClient.getUser("Name")).thenReturn(null);

        ResponseEntity<ProductDTO> response = purchaseController.purchaseUser("Name", PRODUCT_ID);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    //todo: you should also test other scenarios like product not found, but who cares
}
