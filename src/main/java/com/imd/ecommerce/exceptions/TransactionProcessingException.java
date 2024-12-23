package com.imd.ecommerce.exceptions;

public class TransactionProcessingException extends RuntimeException{

    public TransactionProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
