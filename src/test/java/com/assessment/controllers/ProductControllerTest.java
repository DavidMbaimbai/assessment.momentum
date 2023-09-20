package com.assessment.controllers;

import com.assessment.controllers.ProductController;
import com.assessment.entity.Product;
import com.assessment.enums.ProductType;
import com.assessment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductId("P1");
        product1.setType(ProductType.RETIREMENT);
        product1.setName("Retirement Fund");
        product1.setCurrentBalance(BigDecimal.valueOf(1000.0));
        product1.setBalance(BigDecimal.valueOf(1500.0));
        products.add(product1);
        when(productService.getAllProducts()).thenReturn(products);
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }


    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product sampleProduct = new Product();
        sampleProduct.setId(productId);
        sampleProduct.setProductId("P1");
        sampleProduct.setType(ProductType.RETIREMENT);
        sampleProduct.setName("Retirement Fund");
        sampleProduct.setCurrentBalance(BigDecimal.valueOf(1000.0));
        sampleProduct.setBalance(BigDecimal.valueOf(1500.0));
        Optional<Product> product = Optional.of(sampleProduct);
        when(productService.getProductById(productId)).thenReturn(product);
        ResponseEntity<Optional<Product>> response = productController.getProductById(productId);
        if (product.isPresent()) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(product, response.getBody());
        } else {
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }


    @Test
    public void testCreateProduct() {
        Product newProduct = new Product();
        newProduct.setProductId("P1");
        newProduct.setType(ProductType.RETIREMENT);
        newProduct.setName("New Product");
        newProduct.setCurrentBalance(BigDecimal.valueOf(500.0));
        newProduct.setBalance(BigDecimal.valueOf(700.0));
        Product createdProduct = newProduct;
        createdProduct.setId(1L);
        when(productService.createProduct(newProduct)).thenReturn(createdProduct);
        ResponseEntity<Product> response = productController.createProduct(newProduct);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
    }


    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setProductId("P1");
        updatedProduct.setType(ProductType.SAVINGS);
        updatedProduct.setName("Updated Product");
        updatedProduct.setCurrentBalance(BigDecimal.valueOf(800.0));
        updatedProduct.setBalance(BigDecimal.valueOf(1000.0));
        Product updatedProductDb = updatedProduct;
        updatedProductDb.setId(productId);
        when(productService.updateProduct(productId, updatedProduct)).thenReturn(updatedProductDb);
        ResponseEntity<Product> response = productController.updateProduct(productId, updatedProduct);
        if (updatedProductDb != null) {
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(updatedProductDb, response.getBody());
        } else {
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }
    }


    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        ResponseEntity<Void> response = productController.deleteProduct(productId);

        verify(productService, times(1)).deleteProduct(productId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
