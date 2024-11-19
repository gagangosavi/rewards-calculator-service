package com.homework.rewardpoints.reward.exception.product;

import com.homework.rewardpoints.reward.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {

    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
