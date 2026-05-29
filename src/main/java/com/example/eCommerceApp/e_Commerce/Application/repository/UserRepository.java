package com.example.eCommerceApp.e_Commerce.Application.repository;

import com.example.eCommerceApp.e_Commerce.Application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}