package com.example.teamviewr.model;

import java.io.Serializable;
import java.util.Objects;

import com.example.teamviewr.controller.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OrderItemId implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemId.class);

    private Integer order;

    private Integer product;

    // No-argument constructor for JPA
    public OrderItemId() {
    }

    // Parameterized constructor for ease of use
    public OrderItemId(Integer order, Integer product) {
        this.order = order;
        this.product = product;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(getOrder(), that.getOrder()) &&
                Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getProduct());
    }

    @Override
    public String toString() {
        return "OrderItemId{" +
                "order=" + order +
                ", product=" + product +
                '}';
    }}