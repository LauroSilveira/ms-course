package com.desuperior.hrpayroll.restcontrollers;

import com.desuperior.hrpayroll.entities.Payment;
import com.desuperior.hrpayroll.services.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/payments")
public class PaymentRestController {

    private PaymentService service;

    @Autowired
    public PaymentRestController(PaymentService service) {
        this.service = service;
    }

    @CircuitBreaker(name = "worker", fallbackMethod = "getPaymentCircuitBreaker")
    @GetMapping(value = "/{workerId}/days/{days}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days) {
        Optional<Payment> payment = Optional.ofNullable(service.getPayment(workerId, days));
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    /**
     * FallBack method of getPayment
     *
     * @param workerId
     * @param days
     * @return a Response Entity of Payment
     */
    public ResponseEntity<Payment> getPaymentCircuitBreaker(Long workerId, Integer days, Exception ex) {
        return ResponseEntity.ok(Payment.builder().name("test").day(2)
                .dailyIncome(10.0)
                .build());

    }
}
