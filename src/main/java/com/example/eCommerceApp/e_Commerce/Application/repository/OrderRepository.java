package com.example.eCommerceApp.e_Commerce.Application.repository;

import com.example.eCommerceApp.e_Commerce.Application.entity.Order;
import com.example.eCommerceApp.e_Commerce.Application.entity.User;
import com.example.eCommerceApp.e_Commerce.Application.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);
}