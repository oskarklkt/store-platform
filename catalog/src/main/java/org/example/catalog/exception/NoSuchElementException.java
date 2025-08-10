package org.example.catalog.exception;

public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException(String id) {
        super("product with id: " + id + " doesn't exists!");
    }
}
