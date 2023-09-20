package com.assessment.service;

import com.assessment.entity.Product;
import com.assessment.enums.ProductType;
import com.assessment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>(); // Create a list of products for testing
        products.add(createProduct(1L, "P1", ProductType.RETIREMENT, "Product 1", BigDecimal.valueOf(1000.0), BigDecimal.valueOf(1500.0)));
        products.add(createProduct(2L, "P2", ProductType.SAVINGS, "Product 2", BigDecimal.valueOf(500.0), BigDecimal.valueOf(800.0)));

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = createProduct(productId, "P1", ProductType.RETIREMENT, "Product 1", BigDecimal.valueOf(1000.0), BigDecimal.valueOf(1500.0));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
    }

    @Test
    public void testCreateProduct() {
        Product newProduct = createProduct(null, "P3", ProductType.SAVINGS, "New Product", BigDecimal.valueOf(500.0), BigDecimal.valueOf(700.0));

        when(productRepository.save(newProduct)).thenReturn(newProduct);

        Product createdProduct = productService.createProduct(newProduct);

        assertNotNull(createdProduct);
        assertEquals(newProduct, createdProduct);
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Product updatedProduct = createProduct(productId, "P1", ProductType.RETIREMENT, "Updated Product", BigDecimal.valueOf(1500.0), BigDecimal.valueOf(2000.0));
        Product existingProduct = createProduct(productId, "P1", ProductType.RETIREMENT, "Product 1", BigDecimal.valueOf(1000.0), BigDecimal.valueOf(1500.0));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        Product updatedProductDb = productService.updateProduct(productId, updatedProduct);

        assertNotNull(updatedProductDb);
        assertEquals(updatedProduct, updatedProductDb);
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    private Product createProduct(Long id, String productId, ProductType type, String name, BigDecimal currentBalance, BigDecimal balance) {
        Product product = new Product();
        product.setId(id);
        product.setProductId(productId);
        product.setType(type);
        product.setName(name);
        product.setCurrentBalance(currentBalance);
        product.setBalance(balance);
        return product;
    }
}
