package org.example.product.exception;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException(String id) {
        super("product with id: " + id + " doesn't exists!");
    }
}
