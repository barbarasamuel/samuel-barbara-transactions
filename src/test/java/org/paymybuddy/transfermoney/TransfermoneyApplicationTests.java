package org.paymybuddy.transfermoney;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TransfermoneyApplicationTests.class})

@EnableConfigurationProperties
@SpringBootApplication
public class TransfermoneyApplicationTests {

	@Test
	void contextLoads() {
	}
	public static void main(String[] args) {
		SpringApplication.run(TransfermoneyApplicationTests.class, args);
	}
}
