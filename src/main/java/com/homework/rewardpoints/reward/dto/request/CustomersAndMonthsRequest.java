package com.homework.rewardpoints.reward.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomersAndMonthsRequest {
    @NotNull(message = "Customer IDs cannot be null")
    @NotEmpty(message = "Please provide a minimum of one customer")
    List<Long> customerIds;

    @NotNull(message = "List of months cannot be null")
    @NotEmpty(message = "Please provide a minimum of one month")
    List<Month> months;
}
