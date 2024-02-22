package com.example.teamviewr.services;

import com.example.teamviewr.model.Order;
import com.example.teamviewr.repository.OrderRepository;
import com.example.teamviewr.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testFindAll() {
        // Setup
        Order order1 = new Order("Order1", "Description1");
        Order order2 = new Order("Order2", "Description2");
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Execute
        List<Order> orders = orderService.getAllOrders();

        // Assert
        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Setup
        Integer orderId = 1;
        Order order = new Order("Order", "Description");
        order.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Execute
        Optional<Order> foundOrder = orderService.getOrderById(orderId);

        // Assert
        assertTrue(foundOrder.isPresent());
        assertEquals(orderId, foundOrder.get().getId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testSaveOrder() {
        // Setup
        Order order = new Order("Order", "Description");
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Execute
        Order savedOrder = orderService.saveOrder(order);

        // Assert
        assertNotNull(savedOrder);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    void testDeleteProduct() {
        // Given
        Integer orderId = 1;
        when(orderRepository.existsById(orderId)).thenReturn(true);

        // When
        orderService.deleteOrder(orderId);

        // Then
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}
