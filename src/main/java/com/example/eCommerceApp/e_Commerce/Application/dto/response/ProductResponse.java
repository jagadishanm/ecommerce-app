package com.example.eCommerceApp.e_Commerce.Application.dto.response;

public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    public ProductResponse() {
    }

    public ProductResponse(Long id,
                           String name,
                           String description,
                           Double price,
                           Integer stock) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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