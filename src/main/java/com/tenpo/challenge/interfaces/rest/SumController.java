package com.tenpo.challenge.interfaces.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.challenge.application.SumService;
import com.tenpo.challenge.domain.repository.TimeProvider;
import com.tenpo.challenge.interfaces.dto.SumErrorResponse;
import com.tenpo.challenge.interfaces.dto.SumRequest;
import com.tenpo.challenge.interfaces.dto.SumResponse;
import com.tenpo.challenge.interfaces.exception.NullOperandException;
import com.tenpo.challenge.interfaces.exception.NullRequestBodyException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class SumController {
    private final TimeProvider timeProvider;
    private final SumService sumService;

    public SumController(SumService sumService, TimeProvider timeProvider) {
        this.sumService = sumService;
        this.timeProvider = timeProvider;
    }

    @PostMapping("/sum")
    public ResponseEntity<?> sum(@RequestBody(required = false) SumRequest request) {
        LocalDateTime now = timeProvider.now();
        try {
            if (request == null){
            throw new NullRequestBodyException("null request body");
            }
            if (request.getOperand1() == null || request.getOperand2() == null){
                throw new NullOperandException("operands cannot be null");
        }
            Double result = sumService.sum(request.getOperand1(), request.getOperand2());

            // implement save async in service and call here
            sumService.saveSuccessCallHistoryAsync(request.getOperand1(), request.getOperand2(), 200D, "/api/sum", now, String.valueOf(result));
        
            return ResponseEntity.ok(new SumResponse(result));

        } catch (NullRequestBodyException | NullOperandException e) {
            Double operand1 = request != null ? request.getOperand1() : null;
            Double operand2 = request != null ? request.getOperand2() : null;
            sumService.saveErrorCallHistoryAsync(operand1, operand2, 400D, "/api/sum", now, e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SumErrorResponse(400D, e.getMessage()));
        }
    }
}
