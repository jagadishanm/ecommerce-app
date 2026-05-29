package com.example.eCommerceApp.e_Commerce.Application.dto.response;

import com.example.eCommerceApp.e_Commerce.Application.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long orderId;

    private OrderStatus status;

    private Double totalAmount;

    private LocalDateTime orderDate;

    private List<OrderItemData> items;

    public OrderResponse() {
    }

    public OrderResponse(Long orderId,
                         OrderStatus status,
                         Double totalAmount,
                         LocalDateTime orderDate,
                         List<OrderItemData> items) {

        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderItemData> getItems() {
        return items;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setItems(List<OrderItemData> items) {
        this.items = items;
    }

    /*
        Nested DTO
    */
    public static class OrderItemData {

        private String productName;

        private Integer quantity;

        private Double price;

        private Double subtotal;

        public OrderItemData() {
        }

        public OrderItemData(String productName,
                             Integer quantity,
                             Double price,
                             Double subtotal) {

            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.subtotal = subtotal;
        }

        public String getProductName() {
            return productName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Double getPrice() {
            return price;
        }

        public Double getSubtotal() {
            return subtotal;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public void setSubtotal(Double subtotal) {
            this.subtotal = subtotal;
        }
    }
}