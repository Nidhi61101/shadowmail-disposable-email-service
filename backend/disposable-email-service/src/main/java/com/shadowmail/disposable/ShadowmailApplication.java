package com.shadowmail.disposable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.shadowmail.disposable.repository")
@EntityScan(basePackages = "com.shadowmail.disposable.model")
@SpringBootApplication(scanBasePackages = "com.shadowmail.*")
public class ShadowmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShadowmailApplication.class, args);
	}

}
