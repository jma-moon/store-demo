package com.jma_moon.store.price.service;

import com.jma_moon.store.price.model.Price;

import java.time.LocalDateTime;

public interface PriceService {

    Price findTheOneWithHighestPriority(long brandId, long productId, LocalDateTime date);

}
