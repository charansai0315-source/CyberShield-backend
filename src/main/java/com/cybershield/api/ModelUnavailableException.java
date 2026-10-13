package com.cybershield.api;

public class ModelUnavailableException extends RuntimeException {
    public ModelUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
