package com.desuperior.hrpayroll.services;

import com.desuperior.hrpayroll.config.FeignClient;
import com.desuperior.hrpayroll.entities.Payment;
import com.desuperior.hrpayroll.entities.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

@Service
@Slf4j
public class PaymentService {

    @LoadBalanced
    private final FeignClient feignClient;

    @Autowired
    public PaymentService(FeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public Payment getPayment(Long workerId, int days) {
        log.info("Request to Microservice Worker with parameters: Id {}, days {}", workerId, days);
        Optional<Worker> response = Optional.ofNullable(feignClient.findById(workerId).getBody());
        log.info("Response of microservice Worker with Feign: {}", response.map(Worker::getId));
        return response.map(worker -> Payment.builder()
                .name(worker.getName())
                .dailyIncome(worker.getDailyIncome())
                .day(days)
                .build()).orElse(Payment.builder().build());
    }
}
