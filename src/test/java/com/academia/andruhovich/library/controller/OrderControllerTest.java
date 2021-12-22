package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.dto.OrderRequestDto;
import com.academia.andruhovich.library.dto.OrderResponseDto;
import com.academia.andruhovich.library.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_DELETE;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_EDIT;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_WRITE;
import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.JsonConvertHelper.convertToJsonString;
import static com.academia.andruhovich.library.util.JsonConvertHelper.getOrderResponseDto;
import static com.academia.andruhovich.library.util.JsonConvertHelper.getOrderResponseDtos;
import static com.academia.andruhovich.library.util.OrderHelper.createNewOrderDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup({
        @Sql(value = "classpath:/sql/prepareTestDB.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:/sql/clearTestDB.sql", executionPhase = AFTER_TEST_METHOD)
})
class OrderControllerTest {

    public static OrderRequestDto newOrderDto = createNewOrderDto();

    @Autowired
    OrderService service;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getOrders() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Set<OrderResponseDto> orderResponseDtos = getOrderResponseDtos(response);

        OrderResponseDto orderResponseDto = orderResponseDtos.iterator().next();
        assertEquals(ID, orderResponseDto.getId());
        assertEquals(newOrderDto.getStatus(), orderResponseDto.getStatus());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_READ, password = "12356")
    @Test
    void getOrder() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders/{id}", ID)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        OrderResponseDto orderResponseDto = getOrderResponseDto(response);

        assertEquals(ID, orderResponseDto.getId());
        assertEquals(newOrderDto.getStatus(), orderResponseDto.getStatus());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_DELETE, password = "12356")
    @Test
    void deleteOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/{id}", ID))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_WRITE, password = "12356")
    @Test
    void createOrder() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/orders")
                        .content(convertToJsonString(newOrderDto))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        OrderResponseDto orderResponseDto = getOrderResponseDto(response);

        assertEquals(2L, orderResponseDto.getId());
        assertEquals(newOrderDto.getStatus(), orderResponseDto.getStatus());
    }

    @WithMockUser(username = "admin_mock", roles = "USER", authorities = AUTHORITY_EDIT, password = "12356")
    @Test
    void updateOrder() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/orders/{id}", ID)
                        .content(convertToJsonString(newOrderDto))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        OrderResponseDto orderResponseDto = getOrderResponseDto(response);

        assertEquals(ID, orderResponseDto.getId());
        assertEquals(newOrderDto.getStatus(), orderResponseDto.getStatus());
    }

}
