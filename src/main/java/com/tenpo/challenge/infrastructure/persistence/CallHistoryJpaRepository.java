package com.tenpo.challenge.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CallHistoryJpaRepository extends JpaRepository<CallHistoryEntity, Long> {
    
}