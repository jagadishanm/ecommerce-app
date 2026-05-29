package com.example.eCommerceApp.e_Commerce.Application.repository;

import com.example.eCommerceApp.e_Commerce.Application.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
