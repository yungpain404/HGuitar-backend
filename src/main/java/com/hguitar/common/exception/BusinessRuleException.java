package com.hguitar.common.exception;

/**
 * Thrown when a requested operation violates a business rule.
 * Maps to HTTP 422 Unprocessable Entity.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
