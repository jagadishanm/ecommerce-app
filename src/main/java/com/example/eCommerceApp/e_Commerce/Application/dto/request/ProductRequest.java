package com.example.eCommerceApp.e_Commerce.Application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Min(value = 1)
    private Double price;

    @NotNull
    @Min(value = 0)
    private Integer stock;

    public ProductRequest() {
    }

    public ProductRequest(String name,
                          String description,
                          Double price,
                          Integer stock) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}