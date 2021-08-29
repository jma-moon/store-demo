package com.jma_moon.store.price.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Price {

    Long getBrandId();
    Long getProductId();
    Long getPriceList();
    BigDecimal getPrice();
    String getCurrency();
    LocalDateTime getLastUpdatedDate();
    boolean wasLastUpdatedAt(LocalDateTime date);

}
