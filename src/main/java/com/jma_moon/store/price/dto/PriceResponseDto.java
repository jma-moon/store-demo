package com.jma_moon.store.price.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PriceResponseDto {

    private Long brandId;
    private Long productId;
    private Long priceList;
    private BigDecimal price;
    private String currency;
    private LocalDateTime date;

}
