package com.shop.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private Long id;

    @Min(1)
    @Max(100000)
    private double quant;

    @Min(1)
    @Max(100000)
    private double price;

    @Min(1)
    @Max(100000000)
    private double total;

    @Min(0)
    @Max(100)
    private Integer nds;

    @Min(0)
    @Max(100000000)
    private double ndstotal;

    @Min(0)
    @Max(1)
    private Integer canceled;

    private Check check;

    private Goods goods;
}
