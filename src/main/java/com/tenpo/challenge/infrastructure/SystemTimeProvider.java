package com.tenpo.challenge.infrastructure;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.tenpo.challenge.domain.repository.TimeProvider;

@Component
public class SystemTimeProvider implements TimeProvider{
    
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
