package org.example.product.exception;

public class DependentServiceUnavailableException extends RuntimeException {
  public DependentServiceUnavailableException(String message, Throwable cause) { super(message, cause); }
}
