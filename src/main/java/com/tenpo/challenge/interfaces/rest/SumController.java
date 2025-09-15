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
        if (request == null){
            sumService.saveCallHistoryAsync(null, null, 400, "/api/sum", java.time.LocalDateTime.now(), "Bad request: null body");
            return ResponseEntity.badRequest().build();
        }
        if (request.getOperand1() == null || request.getOperand2() == null){
            sumService.saveCallHistoryAsync(null, null, 500, "/api/sum", java.time.LocalDateTime.now(), "Bad ~request: null operands");
            return ResponseEntity.badRequest().build();
        }
        double result = sumService.sum(request.getOperand1(), request.getOperand2());

        // implement save async in service and call here
        sumService.saveCallHistoryAsync(request.getOperand1(), request.getOperand2(), 200, "/api/sum", java.time.LocalDateTime.now(), String.valueOf(result));
        
        return ResponseEntity.ok(new SumResponse(result));
    }
    
}
