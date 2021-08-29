package com.jma_moon.store.price.controller;

import com.jma_moon.store.price.model.Price;
import com.jma_moon.store.price.dto.PriceResponseDto;
import com.jma_moon.store.price.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PriceController {

    public static final String PRICE_NOT_FOUND_MESSAGE = "Price not found";

    private final PriceService priceService;

    @GetMapping("/brands/{brandId}/products/{productId}/prices/{date}")
    public PriceResponseDto findTheOneWithHighestPriority(
            @PathVariable("brandId") long brandId,
            @PathVariable("productId") long productId,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        Price price = priceService.findTheOneWithHighestPriority(brandId, productId, date);

        if (price == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, PRICE_NOT_FOUND_MESSAGE);
        }

        return mapToResponse(price, date);
    }

    private PriceResponseDto mapToResponse(Price price, LocalDateTime date) {
        return PriceResponseDto.builder()
                .brandId(price.getBrandId())
                .productId(price.getProductId())
                .priceList(price.getPriceList())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .date(date)
                .build();
    }

}
