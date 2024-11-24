package com.homework.rewardpoints.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardPointsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void testGetRewardsSummary() throws Exception {
//        mockMvc.perform(get("/api/reward-points/get-summary")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("[]")) // Empty input
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").isNumber());
//    }

    @Test
    public void testGetThreeMonthsSummary_success() throws Exception {
        mockMvc.perform(get("/api/reward-points/get-3months-summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNumber());
    }
}
