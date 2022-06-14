package com.desuperior.hrpayroll.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker implements Serializable {

    private static final long serialVersionUID = 1077182281058319777L;

    private Long id;
    private String name;
    private Double dailyIncome;
}
