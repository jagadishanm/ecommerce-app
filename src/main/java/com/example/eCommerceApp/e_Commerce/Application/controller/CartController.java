package com.example.eCommerceApp.e_Commerce.Application.controller;

import com.example.eCommerceApp.e_Commerce.Application.dto.request.CartItemRequest;
import com.example.eCommerceApp.e_Commerce.Application.dto.response.CartResponse;
import com.example.eCommerceApp.e_Commerce.Application.service.CartService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /*
        Add to cart
    */
    @PostMapping("/add")
    public ResponseEntity<CartResponse>
    addToCart(
            @Valid @RequestBody
            CartItemRequest request) {

        return ResponseEntity.ok(
                cartService.addToCart(request)
        );
    }

    /*
        View cart
    */
    @GetMapping
    public ResponseEntity<CartResponse>
    getCart() {

        return ResponseEntity.ok(
                cartService.getCart()
        );
    }

    /*
        Remove item
    */
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String>
    removeCartItem(
            @PathVariable Long cartItemId) {

        cartService.removeCartItem(cartItemId);

        return ResponseEntity.ok(
                "Item removed from cart"
        );
    }
}