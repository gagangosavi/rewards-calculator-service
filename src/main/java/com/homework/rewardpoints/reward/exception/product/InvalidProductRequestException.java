package com.homework.rewardpoints.reward.exception.product;

public class InvalidProductRequestException extends RuntimeException{
    public InvalidProductRequestException(String message) {
        super(message);
    }

    public InvalidProductRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
