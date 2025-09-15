package com.tenpo.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableAsync
public class ChallengeApplication {

	@RequestMapping("/")
	public String home() {
		return "OK";
	}

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

}
