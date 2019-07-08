package com.example.webfluxdemo;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.webfluxdemo.model.Employee;
import com.example.webfluxdemo.repository.EmployeeRepository;

/**
 * @author : Bhawani Singh Shekhawat
 * @date : 06-July-2019
 */
@SpringBootApplication
public class WebfluxDemoApplication {

	@Bean
	CommandLineRunner employees(EmployeeRepository employeeRepository) {

		return args -> {
			employeeRepository.deleteAll().subscribe(null, null, () -> {
				Stream.of(new Employee("Peter", "Create Employee"), new Employee("Sam", "Create Employee"),
						new Employee("Ryan", "Create Employee"), new Employee("Ram", "Create Employee"))
						.forEach(employee -> {
							employeeRepository.save(employee).subscribe(System.out::println);

						});
			});
		};

	}

	public static void main(String[] args) {
		SpringApplication.run(WebfluxDemoApplication.class, args);
	}

}
