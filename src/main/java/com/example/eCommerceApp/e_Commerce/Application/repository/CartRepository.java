package com.example.eCommerceApp.e_Commerce.Application.repository;

import com.example.eCommerceApp.e_Commerce.Application.entity.Cart;
import com.example.eCommerceApp.e_Commerce.Application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}