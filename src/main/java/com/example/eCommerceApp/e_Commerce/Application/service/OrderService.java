package com.example.eCommerceApp.e_Commerce.Application.service;

import com.example.eCommerceApp.e_Commerce.Application.dto.response.OrderResponse;
import com.example.eCommerceApp.e_Commerce.Application.entity.*;
import com.example.eCommerceApp.e_Commerce.Application.entity.enums.OrderStatus;
import com.example.eCommerceApp.e_Commerce.Application.exception.ResourceNotFoundException;
import com.example.eCommerceApp.e_Commerce.Application.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    /*
        Place order
    */
    public OrderResponse placeOrder() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart is empty"));

        if (cart.getItems().isEmpty()) {

            throw new RuntimeException(
                    "Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);

        order.setStatus(OrderStatus.PENDING);

        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems =
                new ArrayList<>();

        double totalAmount = 0;

        /*
            Convert cart items -> order items
        */
        for (CartItem cartItem : cart.getItems()) {

            Product product =
                    cartItem.getProduct();

            /*
                Stock validation
            */
            if (product.getStock()
                    < cartItem.getQuantity()) {

                throw new RuntimeException(
                        "Insufficient stock for product: "
                                + product.getName());
            }

            /*
                Reduce stock
            */
            product.setStock(
                    product.getStock()
                            - cartItem.getQuantity()
            );

            productRepository.save(product);

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProduct(product);

            orderItem.setQuantity(
                    cartItem.getQuantity());

            /*
                Snapshot product price
            */
            orderItem.setPrice(
                    product.getPrice());

            orderItems.add(orderItem);

            totalAmount +=
                    product.getPrice()
                            * cartItem.getQuantity();
        }

        order.setItems(orderItems);

        order.setTotalAmount(totalAmount);

        Order savedOrder =
                orderRepository.save(order);

        /*
            Save order items
        */
        orderItemRepository.saveAll(orderItems);

        /*
            Clear cart
        */
        cart.getItems().clear();

        cartRepository.save(cart);

        return mapToOrderResponse(savedOrder);
    }

    /*
        Current user's orders
    */
    public List<OrderResponse> getMyOrders() {

        User user = getCurrentUser();

        return orderRepository.findByUser(user)
                .stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    /*
        Get order by id
    */
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));

        return mapToOrderResponse(order);
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
    private OrderResponse mapToOrderResponse(
            Order order) {

        List<OrderResponse.OrderItemData>
                items = new ArrayList<>();

        for (OrderItem item : order.getItems()) {

            double subtotal =
                    item.getPrice()
                            * item.getQuantity();

            items.add(
                    new OrderResponse.OrderItemData(
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getPrice(),
                            subtotal
                    )
            );
        }

        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getOrderDate(),
                items
        );
    }

    public OrderResponse updateOrderStatus(
            Long orderId,
            OrderStatus status) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found"));

        order.setStatus(status);

        Order updatedOrder =
                orderRepository.save(order);

        return mapToOrderResponse(updatedOrder);
    }
}