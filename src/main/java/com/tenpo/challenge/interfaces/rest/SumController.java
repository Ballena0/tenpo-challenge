package com.tenpo.challenge.interfaces.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenpo.challenge.application.SumService;
import com.tenpo.challenge.application.useCases.GetAllHistory;
import com.tenpo.challenge.domain.model.CallHistory;
import com.tenpo.challenge.domain.repository.TimeProvider;
import com.tenpo.challenge.interfaces.dto.CallHistoryResponse;
import com.tenpo.challenge.interfaces.dto.SumErrorResponse;
import com.tenpo.challenge.interfaces.dto.SumRequest;
import com.tenpo.challenge.interfaces.dto.SumResponse;
import com.tenpo.challenge.interfaces.exception.NullOperandException;
import com.tenpo.challenge.interfaces.exception.NullRequestBodyException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class SumController {
    private final TimeProvider timeProvider;
    private final SumService sumService;
    private final GetAllHistory getAllHistory;

    public SumController(SumService sumService, TimeProvider timeProvider, GetAllHistory getAllHistory) {
        this.sumService = sumService;
        this.timeProvider = timeProvider;
        this.getAllHistory = getAllHistory;
    }

    @PostMapping("/sum")
    @Operation(summary = "Sum two numbers", description = "Returns the sum of two numbers provided in the request body")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
        content = @Content(schema = @Schema(implementation = SumResponse.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input, such as null operands or null request body",
        content = @Content(schema = @Schema(implementation = SumErrorResponse.class))
        )
    })
    public ResponseEntity<?> sum(@RequestBody(required = false) SumRequest request) {
        LocalDateTime now = timeProvider.now();
        try {
            if (request == null){
            throw new NullRequestBodyException("null request body");
            }
            if (request.getOperand1() == null || request.getOperand2() == null){
                throw new NullOperandException("operands cannot be null");
        }
            Mono<SumResponse> result = sumService.sum(request.getOperand1(), request.getOperand2())
                .flatMap(total -> Mono.<Double>fromCallable(() -> {
                    sumService.saveSuccessCallHistoryAsync(
                            request.getOperand1(),
                            request.getOperand2(),
                            200D,
                            "/api/sum",
                            now,
                            total.toString()
                    );
                    return total;
                }).subscribeOn(Schedulers.boundedElastic()))
                .map(total -> new SumResponse(total));


            // implement save async in service and call here
        
            return ResponseEntity.ok(result);

        } catch (NullRequestBodyException | NullOperandException e) {
            Double operand1 = request != null ? request.getOperand1() : null;
            Double operand2 = request != null ? request.getOperand2() : null;
            sumService.saveErrorCallHistoryAsync(operand1, operand2, 400D, "/api/sum", now, e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SumErrorResponse(400D, e.getMessage()));
        }
    }

    @GetMapping("/history")
    @Operation(summary = "Get all calls history", description = "Returns a list of all calls history made to the /sum endpoint")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
        content = @Content(schema = @Schema(implementation = CallHistoryResponse.class)))})
    public ResponseEntity<List<CallHistoryResponse>> getAllHistory() {
        List<CallHistory> history = getAllHistory.getAllhistory();
        List<CallHistoryResponse> response = history.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    private CallHistoryResponse convertToResponse(CallHistory callHistory) {
        return new CallHistoryResponse(
                callHistory.getId(),
                callHistory.getOperand1(),
                callHistory.getOperand2(),
                callHistory.getEndpoint(),
                callHistory.getTimestamp(),
                callHistory.getStatusCode(),
                callHistory.getResponse()
        );
    }
}
