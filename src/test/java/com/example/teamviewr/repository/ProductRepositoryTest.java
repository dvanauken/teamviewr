package com.example.teamviewr.repository;

import com.example.teamviewr.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveProduct() {
        // Create a new product and save it
        Product product = new Product("Test Name", "Test Description");
        Product savedProduct = productRepository.save(product);

        // Verify the saved product has an ID (i.e., it was persisted)
        assertThat(savedProduct.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        // Persist a new product directly using entityManager to bypass the repository
        Product product = new Product("Test Name", "Test Description");
        Product savedProduct = entityManager.persistFlushFind(product); // Assuming persistFlushFind is a typo. Correct method is persistAndFlush

        // Attempt to find the persisted product using the repository
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        // Verify the product is found and matches the saved product
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get()).isEqualToComparingFieldByField(savedProduct);
    }

    @Test
    public void testDeleteById() {
        // Persist a new product
        Product product = new Product("Test Name", "Test Description");
        Product savedProduct = entityManager.persistAndFlush(product);

        // Delete the product by ID using the repository
        productRepository.deleteById(savedProduct.getId());

        // Verify the product was deleted
        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
        assertThat(deletedProduct).isNotPresent();
    }

    @Test
    public void testUpdateProduct() {
        // Persist a new product
        Product product = new Product("Test Name", "Test Description");
        Product savedProduct = entityManager.persistAndFlush(product);

        // Update the product details
        savedProduct.setName("Updated Name");
        savedProduct.setDescription("Updated Description");
        Product updatedProduct = productRepository.save(savedProduct);

        // Verify the updated product is persisted
        Optional<Product> foundUpdatedProduct = productRepository.findById(updatedProduct.getId());
        assertThat(foundUpdatedProduct).isPresent();
        assertThat(foundUpdatedProduct.get().getName()).isEqualTo("Updated Name");
        assertThat(foundUpdatedProduct.get().getDescription()).isEqualTo("Updated Description");
    }
}
