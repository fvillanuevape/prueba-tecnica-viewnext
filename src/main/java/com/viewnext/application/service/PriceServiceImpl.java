package com.viewnext.application.service;


import com.viewnext.infraestructure.entity.PriceEntity;
import com.viewnext.infraestructure.adapter.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PriceServiceImpl implements PriceService {

    private PriceRepository repo;


    public PriceServiceImpl(PriceRepository repo) {
        this.repo = repo;
    }
    @Override
    public Iterable<PriceEntity> searchByStartDateAndProductIdAndBrandId(String startDate, int productId, int brandId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localStartDate= LocalDateTime.parse(startDate, formatter);
        return repo.findByStartDateAndProductIdAndBrandId(localStartDate,productId,brandId);
    }


}
