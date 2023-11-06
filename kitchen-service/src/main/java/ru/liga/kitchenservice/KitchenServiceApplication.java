package ru.liga.kitchenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "ru.liga.common")
public class KitchenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KitchenServiceApplication.class, args);
	}

}
