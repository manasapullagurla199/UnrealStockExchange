package com.app.tradeServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class TradeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeServerApplication.class, args);
	}

}
