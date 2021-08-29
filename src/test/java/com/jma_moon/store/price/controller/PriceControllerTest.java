package com.jma_moon.store.price.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql("/price-test-data.sql")
class PriceControllerTest {

    private final MockMvc mockMvc;

    private long brandId;
    private long productId;

    @Autowired
    public PriceControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp() {
        brandId = 1;
        productId = 35455;
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void findTheOneWithHighestPriority(String dateString, int status, double price) throws Exception {
        String urlTemplate = String.format("/brands/%d/products/%d/prices/%s", brandId, productId, dateString);

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().is(status))
                .andExpect(jsonPath("$.price").value(price));
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of("2020-06-14T10:00:00", 200, 35.5),
                Arguments.of("2020-06-14T16:00:00", 200, 25.45),
                Arguments.of("2020-06-15T10:00:00", 200, 30.5),
                Arguments.of("2020-06-16T21:00:00", 200, 38.95)
        );
    }

}