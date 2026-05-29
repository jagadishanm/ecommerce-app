package com.example.eCommerceApp.e_Commerce.Application.repository;

import com.example.eCommerceApp.e_Commerce.Application.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}