package br.com.michel.hercules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HerculesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HerculesApplication.class, args);
	}

}
