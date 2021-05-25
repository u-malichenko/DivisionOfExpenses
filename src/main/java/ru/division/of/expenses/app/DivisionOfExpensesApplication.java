package ru.division.of.expenses.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@PropertySource("classpath:secured.properties")
public class DivisionOfExpensesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DivisionOfExpensesApplication.class, args);
	}

}
