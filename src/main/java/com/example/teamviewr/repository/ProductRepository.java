package com.example.teamviewr.repository;

import com.example.teamviewr.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}