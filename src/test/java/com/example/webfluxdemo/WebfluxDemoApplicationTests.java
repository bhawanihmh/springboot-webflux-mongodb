package com.example.webfluxdemo;

import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.webfluxdemo.model.Employee;
import com.example.webfluxdemo.repository.EmployeeRepository;

import reactor.core.publisher.Mono;

/**
 * @author : Bhawani Singh Shekhawat 
 * @date : 06-July-2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee("Bhawani Singh", "Create Employee");

		webTestClient.post().uri("/employees")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(employee), Employee.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Bhawani Singh");
	}

	@Test
    public void testGetAllEmployees() {
	    webTestClient.get().uri("/employees")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Employee.class);
    }

    @Test
    public void testGetSingleEmployee() {
        Employee employee = employeeRepository.save(new Employee("Kartika Shekhawat","Get Employee")).block();

        webTestClient.get()
                .uri("/employees/{id}", Collections.singletonMap("id", employee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = employeeRepository.save(new Employee("Bhawani Singh Shekhawat","Update Employee")).block();

        Employee newEmployeeData = new Employee("Karanveer Singh Shekhawat","New Employee Data");

        webTestClient.put()
                .uri("/employees/{id}", Collections.singletonMap("id", employee.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(newEmployeeData), Employee.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.name").isEqualTo("Karanveer Singh Shekhawat");
    }

    @Test
    public void testDeleteEmployee() {
	    Employee employee = employeeRepository.save(new Employee("Bhawani Singh", "Delete Employee")).block();

	    webTestClient.delete()
                .uri("/employees/{id}", Collections.singletonMap("id",  employee.getId()))
                .exchange()
                .expectStatus().isOk();
    }
}