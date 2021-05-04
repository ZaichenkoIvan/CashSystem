package com.shop.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Check {

    private Long id;

    private Date crtime;

    @Min(1)
    @Max(10000000)
    private double total;

    @Min(0)
    @Max(100)
    private double discount;

    @Min(0)
    @Max(1)
    private Integer canceled;

    @Min(0)
    @Max(1)
    private Integer registration;

    private User creator;
}
