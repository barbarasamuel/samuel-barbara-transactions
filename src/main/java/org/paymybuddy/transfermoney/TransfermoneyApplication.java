package org.paymybuddy.transfermoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"org.paymybuddy.transfermoney.controller"})
public class TransfermoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransfermoneyApplication.class, args);
	}

}
