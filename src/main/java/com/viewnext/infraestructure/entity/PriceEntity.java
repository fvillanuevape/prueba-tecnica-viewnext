package com.viewnext.infraestructure.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "Prices")
@Data
public class PriceEntity {

    @Id
    @Column(name = "priceId")
    @GeneratedValue(generator = "prices_generator")
    @SequenceGenerator(name = "prices_generator", initialValue = 10)
    private int id;

    @NotNull
    @Column(name = "brandId")
    @Positive
    private int  brandId;

    @Column(name = "startDate",columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "endDate",columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Column(name = "priceList")
    @Positive
    private int priceList;

    @Column(name = "productId")
    @Positive
    private int productId;

    @Column(name = "priority")
    @Positive
    private int priority;

    @Positive
    @Column(name = "price")
    private double price;

    @Column(name = "curr")
    private String curr;
}
