package com.example.teamviewr.controller;

import com.example.teamviewr.model.OrderItem;
import com.example.teamviewr.model.OrderItemId;
import com.example.teamviewr.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private static final Logger logger = LoggerFactory.getLogger(OrderItem.class);


    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Operation(summary = "Get a list of all order items")
    @ApiResponse(responseCode = "200", description = "List of order items retrieved successfully")
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems); // Return the list with a 200 OK status
    }

    @Operation(summary = "Get an order item by ID")
    @ApiResponse(responseCode = "200", description = "Order item retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Order item not found")
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable OrderItemId id) {
        Optional<OrderItem> orderItem = orderItemService.getOrderItemById(id);
        return orderItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new order item")
    @ApiResponse(responseCode = "201", description = "Order item created successfully")
    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@Valid @RequestBody OrderItem orderItem) {
        OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);
        return ResponseEntity.status(201).body(savedOrderItem); // Return the created order item with a 201 Created status
    }

    @Operation(summary = "Update an existing order item")
    @ApiResponse(responseCode = "200", description = "Order item updated successfully")
    @ApiResponse(responseCode = "404", description = "Order item not found")
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable OrderItemId id, @Valid @RequestBody OrderItem orderItemDetails) {
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDetails);
        if (updatedOrderItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedOrderItem); // Return the updated order item with a 200 OK status
    }

    @Operation(summary = "Delete an order item by ID")
    @ApiResponse(responseCode = "204", description = "Order item deleted successfully")
    @ApiResponse(responseCode = "404", description = "Order item not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable OrderItemId id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
