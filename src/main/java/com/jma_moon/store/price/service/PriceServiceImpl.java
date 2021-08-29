package com.jma_moon.store.price.service;

import com.jma_moon.store.price.entity.PriorityProjection;
import com.jma_moon.store.price.model.Price;
import com.jma_moon.store.price.repository.PriceRepository;
import com.jma_moon.store.price.service.priority.PricePriority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService {

    public static final String NO_PRICE_FOUND_LOG_ENTRY = "No price found. brandId = {}, productId = {}, date = {}";
    public static final String MORE_THAN_ONE_PRICE_FOUND_LOG_ENTRY = "More than one price found. brandId = {}, productId = {}, date = {}";

    private final PriceRepository priceRepository;
    private final PricePriority pricePriority;

    @Override
    public Price findTheOneWithHighestPriority(long brandId, long productId, LocalDateTime date) {
        Optional<PriorityProjection> optionalPriorityProjection = priceRepository.findMaxPriority(brandId, productId, date);
        if (!optionalPriorityProjection.isPresent()) {
            log.info(NO_PRICE_FOUND_LOG_ENTRY, brandId, productId, date);
            return null;
        }
        PriorityProjection priorityProjection = optionalPriorityProjection.get();
        List<? extends Price> prices = priceRepository.findAll(brandId, productId, date, priorityProjection.getPriority());

        if (prices.isEmpty()) {
            log.info(NO_PRICE_FOUND_LOG_ENTRY, brandId, productId, date);
            return null;
        }
        if (prices.size() > 1) {
            log.info(MORE_THAN_ONE_PRICE_FOUND_LOG_ENTRY, brandId, productId, date);
            return pricePriority.prioritize(prices);
        }

        return prices.get(0);
    }

}
