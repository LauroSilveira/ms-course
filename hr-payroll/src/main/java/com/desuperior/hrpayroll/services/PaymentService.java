package com.desuperior.hrpayroll.services;

import com.desuperior.hrpayroll.config.FeignClient;
import com.desuperior.hrpayroll.entities.Payment;
import com.desuperior.hrpayroll.entities.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
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

        return response.map(worker -> Payment.builder()
                .name(worker.getName())
                .dailyIncome(worker.getDailyIncome())
                .day(days)
                .build()).orElse(null);
    }
}
