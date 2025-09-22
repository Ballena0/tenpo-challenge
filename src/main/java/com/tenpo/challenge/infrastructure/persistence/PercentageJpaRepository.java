package com.tenpo.challenge.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PercentageJpaRepository extends JpaRepository<PercentageEntity, String> {
    
}
