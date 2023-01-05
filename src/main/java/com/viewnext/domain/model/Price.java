package com.viewnext.domain.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Price {


    private int id;
    private int  brandId;

    private LocalDateTime startDate;


    private LocalDateTime endDate;

    private int priceList;

    private int productId;

    private int priority;

    private double price;

    private String curr;
}
