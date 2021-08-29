package com.jma_moon.store.price.repository;

import com.jma_moon.store.price.entity.PriceEntity;
import com.jma_moon.store.price.entity.PriorityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT MAX(p2.priority) AS priority " +
            ",p2.brandId AS brandId " +
            ",p2.productId AS productId " +
            "FROM PriceEntity p2 " +
            "WHERE p2.brandId =:brandId " +
            "AND p2.productId =:productId " +
            "AND p2.startDate <= :date " +
            "AND p2.endDate >= :date " +
            "GROUP BY p2.brandId, p2.productId ")
    Optional<PriorityProjection> findMaxPriority(
            @Param("brandId") long brandId,
            @Param("productId") long productId,
            @Param("date") LocalDateTime date);

    @Query("SELECT DISTINCT p1 " +
            "FROM PriceEntity p1 " +
            "WHERE p1.brandId =:brandId " +
            "AND p1.productId =:productId " +
            "AND p1.startDate <= :date " +
            "AND p1.endDate >= :date " +
            "AND p1.priority = :priority ")
    List<PriceEntity> findAll(
            @Param("brandId") long brandId,
            @Param("productId") long productId,
            @Param("date") LocalDateTime date,
            @Param("priority") int priority);

}
