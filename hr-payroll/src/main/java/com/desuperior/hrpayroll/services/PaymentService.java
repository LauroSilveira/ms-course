package com.desuperior.hrpayroll.services;

import com.desuperior.hrpayroll.config.FeignClient;
import com.desuperior.hrpayroll.entities.Payment;
import com.desuperior.hrpayroll.entities.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @LoadBalanced
    private final FeignClient feignClient;

    @Autowired
    public PaymentService(FeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public Payment getPayment(Long workerId, int days) {
        Optional<Worker> response = Optional.ofNullable(feignClient.findById(workerId).getBody());

        if (response.isPresent()) {
            return Payment.builder()
                    .name(response.get().getName())
                    .dailyIncome(response.get().getDailyIncome())
                    .day(days)
                    .build();
        }else {
            return Payment.builder().build();
        }
    }
}
