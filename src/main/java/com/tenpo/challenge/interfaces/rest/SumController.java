package com.tenpo.challenge.interfaces.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.challenge.application.SumService;
import com.tenpo.challenge.interfaces.dto.SumRequest;
import com.tenpo.challenge.interfaces.dto.SumResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class SumController {
    private final SumService sumService;

    public SumController(SumService sumService) {
        this.sumService = sumService;
    }

    @PostMapping("/sum")
    public ResponseEntity<SumResponse> sum(@RequestBody SumRequest request) {
        double result = sumService.sum(request.getA(), request.getB());

        // implement save async in service and call here
        sumService.saveCallHistory(request.getA(), request.getB(), 200, "/api/sum", java.time.LocalDateTime.now(), String.valueOf(result));
        
        return ResponseEntity.ok(new SumResponse(result));
    }
    
}
