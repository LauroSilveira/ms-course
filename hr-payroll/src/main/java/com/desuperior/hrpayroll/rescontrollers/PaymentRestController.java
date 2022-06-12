package com.desuperior.hrpayroll.rescontrollers;

import com.desuperior.hrpayroll.entities.Payment;
import com.desuperior.hrpayroll.services.PaymentService;
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

    public PaymentRestController(PaymentService service) {
        this.service = service;
    }

    @GetMapping(value = "/{workerId}/days/{days}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days) {
        Optional<Payment> payment = Optional.ofNullable(service.getPayment(workerId, days));
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }
}
