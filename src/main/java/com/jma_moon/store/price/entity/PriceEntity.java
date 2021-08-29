package com.jma_moon.store.price.entity;

import com.jma_moon.store.price.model.Price;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
@Getter
@ToString
public class PriceEntity implements Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BRAND_ID")
    private Long brandId;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "PRICE_LIST_ID")
    private Long priceList;
    @Column(name = "START_DATE")
    private LocalDateTime startDate;
    @Column(name = "END_DATE")
    private LocalDateTime endDate;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "CURR")
    private String currency;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;
    @Column(name = "LAST_UPDATED_DATE")
    private LocalDateTime lastUpdatedDate;

    @Override
    public boolean wasLastUpdatedAt(LocalDateTime date) {
        if (lastUpdatedDate == null) {
            return date == null;
        }

        return lastUpdatedDate.equals(date);
    }

}
