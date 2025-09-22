package com.tenpo.challenge.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
class SchedulerConfig {
    @Bean
    Scheduler miScheduler() {
        return Schedulers.boundedElastic();
    }
}
