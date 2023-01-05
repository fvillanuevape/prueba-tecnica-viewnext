package com.viewnext.application.service;

import com.viewnext.infraestructure.entity.PriceEntity;

public interface PriceService {
    Iterable<PriceEntity> searchByStartDateAndProductIdAndBrandId(String startDate, int productId, int brandId);
}
