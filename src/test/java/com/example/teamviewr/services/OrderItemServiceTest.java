package com.example.teamviewr.services;

import com.example.teamviewr.model.Order;
import com.example.teamviewr.model.OrderItem;
import com.example.teamviewr.model.OrderItemId;
import com.example.teamviewr.model.Product;
import com.example.teamviewr.repository.OrderItemRepository;
import com.example.teamviewr.service.OrderItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void testSaveOrderItem() {
        Order order = new Order("Order1", "Description1");
        Product product = new Product("Product1", "Description1");
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(5);

        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);

        assertNotNull(savedOrderItem);
        assertEquals(5, savedOrderItem.getQuantity());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void testFindById() {
        // Simulate setting IDs, assuming they're generated and not manually set
        Integer orderId = 1; // Example order ID
        Integer productId = 1; // Example product ID

        Order order = new Order("Order2", "Description2");
        order.setId(orderId); // Simulating the ID setting that would normally be done by JPA

        Product product = new Product("Product2", "Description2");
        product.setId(productId); // Simulating the ID setting

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(10);

        OrderItemId orderItemId = new OrderItemId(orderId, productId);
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));

        Optional<OrderItem> foundOrderItem = orderItemService.getOrderItemById(orderItemId);

        assertTrue(foundOrderItem.isPresent());
        assertEquals(10, foundOrderItem.get().getQuantity());
        verify(orderItemRepository, times(1)).findById(orderItemId);
    }
}
