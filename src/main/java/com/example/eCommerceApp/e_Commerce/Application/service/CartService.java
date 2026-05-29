package com.example.eCommerceApp.e_Commerce.Application.service;

import com.example.eCommerceApp.e_Commerce.Application.dto.request.CartItemRequest;
import com.example.eCommerceApp.e_Commerce.Application.dto.response.CartResponse;
import com.example.eCommerceApp.e_Commerce.Application.entity.Cart;
import com.example.eCommerceApp.e_Commerce.Application.entity.CartItem;
import com.example.eCommerceApp.e_Commerce.Application.entity.Product;
import com.example.eCommerceApp.e_Commerce.Application.entity.User;
import com.example.eCommerceApp.e_Commerce.Application.repository.CartItemRepository;
import com.example.eCommerceApp.e_Commerce.Application.repository.CartRepository;
import com.example.eCommerceApp.e_Commerce.Application.repository.ProductRepository;
import com.example.eCommerceApp.e_Commerce.Application.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    /*
        Add product to cart
    */
    public CartResponse addToCart(
            CartItemRequest request) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found"));

        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);

        cartItem.setProduct(product);

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        cart.getItems().add(cartItem);

        return mapToCartResponse(cart);
    }

    /*
        View cart
    */
    public CartResponse getCart() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found"));

        return mapToCartResponse(cart);
    }

    /*
        Remove cart item
    */
    public void removeCartItem(Long cartItemId) {

        CartItem item = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart item not found"));

        cartItemRepository.delete(item);
    }

    /*
        Current logged-in user
    */
    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));
    }

    /*
        Entity -> DTO
    */
    private CartResponse mapToCartResponse(
            Cart cart) {

        List<CartResponse.CartItemData> itemList =
                new ArrayList<>();

        double total = 0;

        for (CartItem item : cart.getItems()) {

            double subtotal =
                    item.getQuantity()
                            * item.getProduct().getPrice();

            total += subtotal;

            itemList.add(
                    new CartResponse.CartItemData(
                            item.getId(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getProduct().getPrice(),
                            subtotal
                    )
            );
        }

        return new CartResponse(
                cart.getId(),
                itemList,
                total
        );
    }
}