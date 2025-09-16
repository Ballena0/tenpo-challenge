package com.tenpo.challenge.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.tenpo.challenge.domain.model.CallHistory;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.driverClassName=org.h2.Driver"
})
public class CallHistoryRepositoryAdapaterIntegrationTest {

    @Autowired
    private CallHistoryRepositoryAdapter callHistoryRespository;

    @Test
    void testShouldPersist() {
        // AAA
        // A
        CallHistory callHistory = new CallHistory(
            null, 5.0, 10.0, "/api/sum",
            java.time.LocalDateTime.now(), 200D, "{\"result\":15.0}");

        // A
        CallHistory result = callHistoryRespository.save(callHistory);

        // assert
        assert result != null;
        assert result.getId() != null;
        assert result.getOperand1().equals(callHistory.getOperand1());
        assert result.getOperand2().equals(callHistory.getOperand2());
        assert result.getEndpoint().equals(callHistory.getEndpoint());
        assert result.getTimestamp().equals(callHistory.getTimestamp());
        assert result.getStatusCode().equals(callHistory.getStatusCode());
        assert result.getResponse().equals(callHistory.getResponse());
    }
}
