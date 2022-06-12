package com.desuperior.hrpayroll.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Double dailyIncome;
    private Integer day;

    public double getTotal() {
        return day * dailyIncome;
    }
}
