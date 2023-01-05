package com.viewnext.infraestructure.adapter;

import com.viewnext.infraestructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface PriceRepository extends JpaRepository<PriceEntity, Integer> {
    Iterable<PriceEntity> findByStartDateAndProductIdAndBrandId(LocalDateTime startDate, int productId, int brandId);
}