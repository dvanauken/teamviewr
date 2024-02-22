package com.example.teamviewr.repository;

import com.example.teamviewr.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        Order order = new Order("Sample Order", "This is a sample order description.");
        Order savedOrder = entityManager.persistAndFlush(order);
        assertThat(savedOrder.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Order order = new Order("Sample Order", "This is a sample order description.");
        // Correctly persist and flush the order to the database
        entityManager.persistAndFlush(order);
        // Directly use orderRepository to find by ID
        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertThat(foundOrder).isPresent().containsSame(order);
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order("Sample Order", "This is a sample order description.");
        // Persist and flush the order to the database
        entityManager.persistAndFlush(order);
        // Use the repository to delete the order
        orderRepository.deleteById(order.getId());
        // Verify the order has been deleted
        Optional<Order> deletedOrder = orderRepository.findById(order.getId());
        assertThat(deletedOrder).isNotPresent();
    }
}
