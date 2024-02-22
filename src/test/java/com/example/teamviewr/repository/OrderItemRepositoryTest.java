package com.example.teamviewr.repository;

import com.example.teamviewr.model.Order;
import com.example.teamviewr.model.OrderItem;
import com.example.teamviewr.model.OrderItemId;
import com.example.teamviewr.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void testSaveAndFindById() {
        // Given
        Order order = new Order("Order1", "Description for Order1");
        order = entityManager.persistFlushFind(order);

        Product product = new Product("Product1", "Description for Product1");
        product = entityManager.persistFlushFind(product);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(10);

        // When
        orderItem = entityManager.persistFlushFind(orderItem);
        OrderItemId orderItemId = new OrderItemId();
        orderItemId.setOrder(order.getId());
        orderItemId.setProduct(product.getId());

        // Then
        OrderItem foundOrderItem = orderItemRepository.findById(orderItemId).orElse(null);
        assertThat(foundOrderItem).isNotNull();
        assertThat(foundOrderItem.getOrder()).isEqualTo(order);
        assertThat(foundOrderItem.getProduct()).isEqualTo(product);
        assertThat(foundOrderItem.getQuantity()).isEqualTo(10);
    }

    @Test
    public void testDeleteById() {
        // Given
        Order order = new Order("Order2", "Description for Order2");
        order = entityManager.persistFlushFind(order);

        Product product = new Product("Product2", "Description for Product2");
        product = entityManager.persistFlushFind(product);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(5);
        orderItem = entityManager.persistFlushFind(orderItem);

        OrderItemId orderItemId = new OrderItemId();
        orderItemId.setOrder(order.getId());
        orderItemId.setProduct(product.getId());

        // When
        orderItemRepository.deleteById(orderItemId);
        entityManager.flush(); // Ensure deletion is executed

        // Then
        boolean exists = orderItemRepository.findById(orderItemId).isPresent();
        assertThat(exists).isFalse();
    }
}
