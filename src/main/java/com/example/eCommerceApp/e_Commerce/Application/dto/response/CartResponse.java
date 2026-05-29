package com.example.eCommerceApp.e_Commerce.Application.dto.response;

import java.util.List;

public class CartResponse {

    private Long cartId;

    private List<CartItemData> items;

    private Double totalAmount;

    public CartResponse() {
    }

    public CartResponse(Long cartId,
                        List<CartItemData> items,
                        Double totalAmount) {

        this.cartId = cartId;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Long getCartId() {
        return cartId;
    }

    public List<CartItemData> getItems() {
        return items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setItems(List<CartItemData> items) {
        this.items = items;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /*
        Nested DTO
    */
    public static class CartItemData {

        private Long cartItemId;

        private String productName;

        private Integer quantity;

        private Double price;

        private Double subtotal;

        public CartItemData() {
        }

        public CartItemData(Long cartItemId,
                            String productName,
                            Integer quantity,
                            Double price,
                            Double subtotal) {

            this.cartItemId = cartItemId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
            this.subtotal = subtotal;
        }

        public Long getCartItemId() {
            return cartItemId;
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

        public void setCartItemId(Long cartItemId) {
            this.cartItemId = cartItemId;
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