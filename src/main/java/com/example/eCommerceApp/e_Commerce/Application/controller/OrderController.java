package com.example.eCommerceApp.e_Commerce.Application.controller;

import com.example.eCommerceApp.e_Commerce.Application.dto.response.OrderResponse;
import com.example.eCommerceApp.e_Commerce.Application.entity.enums.OrderStatus;
import com.example.eCommerceApp.e_Commerce.Application.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /*
        Place order
    */
    @PostMapping("/place")
    public ResponseEntity<OrderResponse>
    placeOrder() {

        return ResponseEntity.ok(
                orderService.placeOrder()
        );
    }

    /*
        Current user's orders
    */
    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>>
    getMyOrders() {

        return ResponseEntity.ok(
                orderService.getMyOrders()
        );
    }

    /*
        Get order by id
    */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>
    getOrderById(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderById(orderId)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse>
    updateOrderStatus(
            @PathVariable Long orderId,

            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(
                        orderId,
                        status
                )
        );
    }
}