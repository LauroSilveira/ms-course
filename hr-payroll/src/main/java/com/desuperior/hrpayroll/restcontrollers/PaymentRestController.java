package com.desuperior.hrpayroll.restcontrollers;

import com.desuperior.hrpayroll.entities.Payment;
import com.desuperior.hrpayroll.services.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/payments")
public class PaymentRestController {

    private PaymentService service;

    @Autowired
    public PaymentRestController(PaymentService service) {
        this.service = service;
    }

    @CircuitBreaker(name = "hr-worker", fallbackMethod = "paymentCircuitBreaker")
    @GetMapping(value = "/{workerId}/days/{days}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days) {
        log.info("Request to getPayment with workerId {}, days {} ", workerId, days);
        Optional<Payment> payment = Optional.ofNullable(service.getPayment(workerId, days));
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    //FallBack method of getPayment
    public ResponseEntity<Payment> paymentCircuitBreaker(Long workerId, Integer days, Exception ex) {
        log.info("Circuit breaker Opened, response will be the request");
        return ResponseEntity.ok(Payment.builder().name(workerId.toString()).day(days)
                .dailyIncome(days.doubleValue())
                .build());
    }
}
