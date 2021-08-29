package com.jma_moon.store.price.service.priority;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PricePriorityConfiguration {

    @Bean
    public PricePriority pricePriority() {
        PricePriorityFirstElement pricePriorityFirstElement = new PricePriorityFirstElement();
        PricePriorityMostRecentUpdatedDate pricePriorityMostRecentUpdatedDate = new PricePriorityMostRecentUpdatedDate();
        pricePriorityMostRecentUpdatedDate.setNext(pricePriorityFirstElement);

        return pricePriorityMostRecentUpdatedDate;
    }

}
