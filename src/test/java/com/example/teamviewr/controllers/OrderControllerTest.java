package com.example.teamviewr.controllers;

import com.example.teamviewr.controller.OrderController;
import com.example.teamviewr.model.Order;
import com.example.teamviewr.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetOrderById() throws Exception {
        // Initialize the order object with an ID to mimic the retrieval of an existing order
        Order order = new Order();
        order.setId(1); // Set the ID to ensure jsonPath("$.id").exists() passes

        // Mock the service call to return an Optional containing the order with an ID
        when(orderService.getOrderById(anyInt())).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1)); // This checks that the response JSON includes an "id" with the value 1
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Initialize the order object with an ID to mimic a successful creation
        Order order = new Order();
        order.setId(1); // Mock the behavior of setting an ID after saving

        // Mock the service call to return the order with an ID
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);

        // Assuming you have a minimal valid JSON representation for creating an order
        // This example assumes the Order requires no fields for simplification
        // Replace "{}" with a valid JSON representation of an Order if needed
        String orderJson = "{}";

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()); // This checks that the response JSON includes an "id"
    }

    @Test
    public void testUpdateOrder() throws Exception {
        // Setup
        Order existingOrder = new Order();
        existingOrder.setId(1); // Set ID to ensure jsonPath("$.id").exists() passes
        // Additional fields can be set as needed to match your Order object requirements

        // Mocking the service to return the prepared object when updateOrder is called
        when(orderService.updateOrder(eq(1), any(Order.class))).thenReturn(existingOrder);

        // Assuming you have a valid JSON representation for the order you're updating
        String orderJson = "{\"id\": 1, \"name\": \"Updated Order Name\", \"quantity\": 10}"; // Example JSON content

        // Perform
        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson)) // Use the valid JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()); // Verifies that the response has an 'id' field
    }


    @Test
    public void testDeleteOrder() throws Exception {
        // No setup needed for void methods
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
        verify(orderService, times(1)).deleteOrder(1);
    }
}
