package com.homework.rewardpoints.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.rewardpoints.reward.dto.request.CustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddCustomer() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("John", "Doe", "john.doe@example.com", "1234567890");

        mockMvc.perform(post("/api/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testAddCustomerValidationError() throws Exception {
        CustomerRequest invalidRequest = new CustomerRequest("", "", "", "");

        mockMvc.perform(post("/api/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.firstName").value("Customer first name cannot be blank"))
                .andExpect(jsonPath("$.errors.lastName").value("Customer last name cannot be blank"))
                .andExpect(jsonPath("$.errors.email").value("Email cannot be blank"))
                .andExpect(jsonPath("$.errors.contact").value("Please provide a valid contact"));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customer/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNumber());
    }

    @Test
    public void testAddCustomersInBulk() throws Exception {
        List<CustomerRequest> customerRequests = new ArrayList<>();
        customerRequests.add(new CustomerRequest("John", "Doe", "john.doe@example.com", "1234567890"));
        customerRequests.add(new CustomerRequest("Jane", "Doe", "jane.doe@example.com", "0987654321"));

        mockMvc.perform(post("/api/customer/bulk-add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequests)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].firstName").value("John"));
    }
}

