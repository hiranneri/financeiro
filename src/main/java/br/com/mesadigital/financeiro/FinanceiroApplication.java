package br.com.mesadigital.financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class FinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApplication.class, args);
	}

}
