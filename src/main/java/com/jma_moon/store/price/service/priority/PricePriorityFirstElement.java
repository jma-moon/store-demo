package com.jma_moon.store.price.service.priority;

import com.jma_moon.store.price.model.Price;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PricePriorityFirstElement implements PricePriority {

    public static final String PRIORITIZING_FIRST_ELEMENT_IN_LIST_LOG_ENTRY = "Prioritizing first element in list. price = {}";

    @Override
    public Price prioritize(List<? extends Price> prices) {
        if (prices == null || prices.isEmpty()) {
            return null;
        }

        Price price = prices.get(0);
        log.info(PRIORITIZING_FIRST_ELEMENT_IN_LIST_LOG_ENTRY, price);

        return price;
    }

}
