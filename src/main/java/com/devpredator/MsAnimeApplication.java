package com.devpredator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.devpredator.msentity.entity"})
public class MsAnimeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MsAnimeApplication.class, args);
	}

}
