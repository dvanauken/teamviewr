package com.example.teamviewr.services;

import com.example.teamviewr.model.Product;
import com.example.teamviewr.repository.ProductRepository;
import com.example.teamviewr.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testFindAll() {

        // Setup
        Product product1 = new Product("Product 1", "Description 1");
        Product product2 = new Product("Product 2", "Description 2");
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Execute
        List<Product> products = productService.getAllProducts();

        // Assert
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Setup
        Integer productId = 1;
        Product product = new Product("Product", "Description");
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Execute
        Optional<Product> foundProduct = productService.getProductById(productId);

        // Assert
        assertTrue(foundProduct.isPresent());
        assertEquals(productId, foundProduct.get().getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testSaveProduct() {
        // Setup
        Product product = new Product("Product", "Description");
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Execute
        Product savedProduct = productService.saveProduct(product);

        // Assert
        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        // Given
        Integer productId = 1;
        when(productRepository.existsById(productId)).thenReturn(true);

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository, times(1)).deleteById(productId);
    }
}
