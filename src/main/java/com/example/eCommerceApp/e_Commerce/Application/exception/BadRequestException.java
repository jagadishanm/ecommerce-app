package com.example.eCommerceApp.e_Commerce.Application.exception;

public class BadRequestException
        extends RuntimeException {

    public BadRequestException(
            String message) {

        super(message);
    }
}