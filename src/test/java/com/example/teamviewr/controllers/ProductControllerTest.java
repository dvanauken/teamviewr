package com.example.teamviewr.controllers;

import com.example.teamviewr.controller.ProductController;
import com.example.teamviewr.model.Product;
import com.example.teamviewr.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProducts() throws Exception {
        Product product1 = new Product("Product1", "Description1");
        product1.setId(1); // Assuming your Product class has an setId method
        Product product2 = new Product("Product2", "Description2");
        product2.setId(2); // Assuming your Product class has an setId method

        given(productService.getAllProducts()).willReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Product1")))
                .andExpect(jsonPath("$[1].name", is("Product2")));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product("Product1", "Description1");
        product.setId(1);

        given(productService.getProductById(1)).willReturn(Optional.of(product));

        mockMvc.perform(get("/api/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product1")))
                .andExpect(jsonPath("$.description", is("Description1")));
    }
    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product("Product1", "Description1");
        given(productService.saveProduct(any(Product.class))).willReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Product1")));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product updatedProduct = new Product("UpdatedProduct", "UpdatedDescription");
        updatedProduct.setId(1);
        given(productService.updateProduct(anyInt(), any(Product.class))).willReturn(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("UpdatedProduct")));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        willDoNothing().given(productService).deleteProduct(1);

        mockMvc.perform(delete("/api/products/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
