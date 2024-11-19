package com.homework.rewardpoints.reward.exception.customer;

public class InvalidCustomerRequestException extends RuntimeException{
    public InvalidCustomerRequestException(String message) {
        super(message);
    }

    public InvalidCustomerRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
