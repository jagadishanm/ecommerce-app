package com.example.eCommerceApp.e_Commerce.Application.entity.enums;

public enum OrderStatus {
    PENDING,      // Order placed, not yet confirmed
    CONFIRMED,    // Payment/stock confirmed
    SHIPPED,      // Handed to delivery
    DELIVERED,    // Reached customer
    CANCELLED     // Cancelled at any stage before delivery
}
