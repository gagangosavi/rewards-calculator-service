package com.homework.rewardpoints.reward.exception.customer;

import com.homework.rewardpoints.reward.exception.BaseException;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BaseException {
    public CustomerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
