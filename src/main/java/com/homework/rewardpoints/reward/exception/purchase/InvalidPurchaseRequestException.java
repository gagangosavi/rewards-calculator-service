package com.homework.rewardpoints.reward.exception.purchase;

public class InvalidPurchaseRequestException extends RuntimeException {
    public InvalidPurchaseRequestException(String message) {
        super(message);
    }

    public InvalidPurchaseRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
