package com.tenpo.challenge.domain.repository;

import java.time.LocalDateTime;

public interface TimeProvider {
    LocalDateTime now();
    
}
