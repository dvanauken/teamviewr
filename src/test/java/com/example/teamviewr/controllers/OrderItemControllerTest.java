package com.example.teamviewr.controllers;

import com.example.teamviewr.controller.OrderItemController;
import com.example.teamviewr.model.Order;
import com.example.teamviewr.model.OrderItem;
import com.example.teamviewr.model.OrderItemId;
import com.example.teamviewr.model.Product;
import com.example.teamviewr.service.OrderItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllOrderItems() throws Exception {
        OrderItem orderItem1 = new OrderItem(/* Initialize fields */);
        OrderItem orderItem2 = new OrderItem(/* Initialize fields */);

        given(orderItemService.getAllOrderItems()).willReturn(Arrays.asList(orderItem1, orderItem2));

        mockMvc.perform(get("/api/order-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

//    @Test
//    public void testGetOrderItemById() throws Exception {
//        OrderItemId orderItemId = new OrderItemId();
//        OrderItem orderItem = new OrderItem();
//
//        given(orderItemService.getOrderItemById(orderItemId)).willReturn(Optional.of(orderItem));
//
//        mockMvc.perform(get("/api/order-items/{id}", orderItemId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(orderItemId)));
//    }

    @Test
    public void testCreateOrderItem() throws Exception {
        // Assume Order and Product classes have a parameterized constructor or setters for ID
        Order order = new Order(); // Assuming an existing constructor or use setters
        order.setId(1); // Mock ID for the order

        Product product = new Product(); // Assuming an existing constructor or use setters
        product.setId(1); // Mock ID for the product

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(5); // Example quantity

        // Mocking the service layer to return the orderItem when saveOrderItem is called
        given(orderItemService.saveOrderItem(any(OrderItem.class))).willReturn(orderItem);

        // Perform the POST request and assert the response
        mockMvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItem)))
                .andExpect(status().isCreated());
    }

//    @Test
//    public void testUpdateOrderItem() throws Exception {
//        OrderItemId orderItemId = new OrderItemId();
//        OrderItem updatedOrderItem = new OrderItem();
//        given(orderItemService.updateOrderItem(orderItemId, any(OrderItem.class))).willReturn(updatedOrderItem);
//
//        mockMvc.perform(put("/api/order-items/{id}", orderItemId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedOrderItem)))
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void testDeleteOrderItem() throws Exception {
//        // Assuming the ID is a simple Long or String for the example
//        OrderItemId orderItemId = new OrderItemId();
//        willDoNothing().given(orderItemService).deleteOrderItem(orderItemId);
//
//        mockMvc.perform(delete("/api/order-items/{id}", orderItemId))
//                .andExpect(status().isNoContent());
//    }

}
