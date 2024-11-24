package com.homework.rewardpoints.reward.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotNull(message = "Customer first name cannot be null")
    @NotBlank(message = "Customer first name cannot be blank")
    private String firstName;

    @NotNull(message = "Customer last name cannot be null")
    @NotBlank(message = "Customer last name cannot be blank")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Contact cannot be null")
    @Size(min = 10,max = 10,message = "Please provide a valid contact")
    private String contact;
}
