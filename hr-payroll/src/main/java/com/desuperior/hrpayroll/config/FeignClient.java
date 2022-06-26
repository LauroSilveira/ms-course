package com.desuperior.hrpayroll.config;

import com.desuperior.hrpayroll.entities.Worker;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component

@org.springframework.cloud.openfeign.FeignClient(name = "hr-worker", path = "/workers")
public interface FeignClient {

    @GetMapping(path = "/{id}")
    ResponseEntity<Worker> findById(@PathVariable Long id);

}
