package com.assessment.service;

import com.assessment.entity.Product;
import com.assessment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
        private final ProductRepository productRepository;

        @Autowired
        public ProductService(ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        public List<Product> getAllProducts() {
            return productRepository.findAll();
        }

        public Optional<Product> getProductById(Long id) {
            return productRepository.findById(id);
        }

        public Product createProduct(Product product) {
            // Perform validation checks
            if (product.getType() == ProductType.RETIREMENT && product.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Balance must be greater than zero for Retirement products.");
            }
            return productRepository.save(product);
        }

        public Product updateProduct(Long id, Product updatedProduct) {
            Optional<Product> existingProductOptional = productRepository.findById(id);
            if (existingProductOptional.isPresent()) {
                Product existingProduct = existingProductOptional.get();
                // Update the properties you want to allow being changed
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setType(updatedProduct.getType());
                existingProduct.setBalance(updatedProduct.getBalance());
                return productRepository.save(existingProduct);
            }
            return null;
        }

        public void deleteProduct(Long id) {
            productRepository.deleteById(id);
        }
    }

}