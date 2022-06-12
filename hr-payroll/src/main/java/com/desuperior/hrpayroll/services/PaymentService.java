package com.desuperior.hrpayroll.services;

import com.desuperior.hrpayroll.entities.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public Payment getPayment(Long workerId, int days){
        return Payment.builder()
                .name("Bob")
                .dailyIncome(200.0)
                .day(days)
                .build();
    }
}
