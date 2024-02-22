package com.example.teamviewr.service;

import com.example.teamviewr.exception.ResourceNotFoundException;
import com.example.teamviewr.model.Order;
import com.example.teamviewr.model.OrderItemId;
import com.example.teamviewr.model.Product;
import com.example.teamviewr.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Integer id, Order orderDetails) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setName(orderDetails.getName());
                    order.setDescription(orderDetails.getDescription());
                    // Update additional fields from orderDetails to order as necessary
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }
}
