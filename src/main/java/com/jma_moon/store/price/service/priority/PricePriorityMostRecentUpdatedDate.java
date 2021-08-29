package com.jma_moon.store.price.service.priority;

import com.jma_moon.store.price.model.Price;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PricePriorityMostRecentUpdatedDate extends PricePriorityAbstract {

    public static final String PRIORITIZING_MOST_RECENT_UPDATE_DATE_LOG_ENTRY = "Prioritizing most recent update date. price = {}";

    protected List<? extends Price> getFilteredPrices(List<? extends Price> prices) {
        LocalDateTime maxLastUpdatedDate = prices.stream()
                .map(Price::getLastUpdatedDate)
                .max(LocalDateTime::compareTo).orElse(null);

        if (maxLastUpdatedDate == null) {
            return prices;
        }

        return prices.stream()
                .filter(price -> price.wasLastUpdatedAt(maxLastUpdatedDate))
                .collect(Collectors.toList());
    }

    @Override
    protected void logPrioritizingPrice(Price price) {
        log.info(PRIORITIZING_MOST_RECENT_UPDATE_DATE_LOG_ENTRY, price);
    }

}
