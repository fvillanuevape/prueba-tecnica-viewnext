package com.viewnext.infraestructure.rest;

import com.viewnext.application.service.PriceService;
import com.viewnext.application.service.PriceServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.viewnext.infraestructure.entity.PriceEntity;

@RestController
@RequestMapping("ms-prices/api/v1")
@Validated
public class PricesController {

    private PriceService service;

    public PricesController(PriceServiceImpl service) {
        this.service = service;
    }

    @GetMapping(value = "prices/search")
    public ResponseEntity<Iterable<PriceEntity>> searchByStartDate(@RequestParam String startDate, @RequestParam int productId, @RequestParam int brandId){
        return new  ResponseEntity<>(service.searchByStartDateAndProductIdAndBrandId(startDate, productId, brandId), HttpStatus.OK);
    }


}