package com.tenpo.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ChallengeApplication {

	@RequestMapping("/")
	public String home() {
		return "OK";
	}

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

}
