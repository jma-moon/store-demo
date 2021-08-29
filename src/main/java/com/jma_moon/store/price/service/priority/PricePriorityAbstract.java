package com.jma_moon.store.price.service.priority;

import com.jma_moon.store.price.model.Price;

import java.util.List;

public abstract class PricePriorityAbstract implements PricePriority {

    private PricePriority next;

    @Override
    public Price prioritize(List<? extends Price> prices) {
        if (prices == null || prices.isEmpty()) {
            return null;
        }

        List<? extends Price> filteredPrices = getFilteredPrices(prices);

        if (filteredPrices.size() > 1) {
            return callNext(prices);
        }

        Price price = filteredPrices.stream().findFirst().orElse(null);
        logPrioritizingPrice(price);

        return price;
    }


    protected abstract List<? extends Price> getFilteredPrices(List<? extends Price> prices);

    private Price callNext(List<? extends Price> prices) {
        if (next == null) {
            return null;
        }

        return next.prioritize(prices);
    }

    protected void logPrioritizingPrice(Price price) {
        // No log entry by default
    }

    public void setNext(PricePriority next) {
        this.next = next;
    }

}
