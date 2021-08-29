package com.jma_moon.store.price.service.priority;

import com.jma_moon.store.price.model.Price;

import java.util.List;

public interface PricePriority {

    Price prioritize(List<? extends Price> prices);

}
