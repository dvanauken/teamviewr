package com.example.teamviewr.repository;

import com.example.teamviewr.model.OrderItem;
import com.example.teamviewr.model.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}