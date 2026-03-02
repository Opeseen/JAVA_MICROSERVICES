package com.ubaclone.eservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EserviceApplication.class, args);
	}

}
