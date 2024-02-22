package com.example.teamviewr.service;

import com.example.teamviewr.exception.ResourceNotFoundException;
import com.example.teamviewr.model.OrderItem;
import com.example.teamviewr.model.OrderItemId;
import com.example.teamviewr.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> getOrderItemById(OrderItemId id) {
        return orderItemRepository.findById(id);
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public boolean deleteOrderItem(OrderItemId id) {
        boolean exists = orderItemRepository.existsById(id);
        if (exists) {
            orderItemRepository.deleteById(id);
        }
        return exists;
    }

    public OrderItem updateOrderItem(OrderItemId id, OrderItem orderItemDetails) {
        return orderItemRepository.findById(id)
            .map(orderItem -> {
                orderItem.setProduct(orderItemDetails.getProduct());
                orderItem.setOrder(orderItemDetails.getOrder());
                orderItem.setQuantity(orderItemDetails.getQuantity());
                return orderItemRepository.save(orderItem);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));    }
}
