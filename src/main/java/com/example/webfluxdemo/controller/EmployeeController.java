package com.example.webfluxdemo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webfluxdemo.model.Employee;
import com.example.webfluxdemo.repository.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author : Bhawani Singh Shekhawat 
 * @date : 06-July-2019
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public Flux<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Mono<Employee> createEmployees(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public Mono<ResponseEntity<Employee>> getEmployeeById(@PathVariable(value = "id") String employeeId) {
        return employeeRepository.findById(employeeId)
                .map(savedEmployee -> ResponseEntity.ok(savedEmployee))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/employees/{id}")
    public Mono<ResponseEntity<Employee>> updateEmployee(@PathVariable(value = "id") String employeeId,
                                                   @Valid @RequestBody Employee employee) {
        return employeeRepository.findById(employeeId)
                .flatMap(existingEmployee -> {
                    existingEmployee.setName(employee.getName());
                    existingEmployee.setDetails(employee.getDetails());
                    return employeeRepository.save(existingEmployee);
                })
                .map(updatedEmployee -> new ResponseEntity<>(updatedEmployee, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/employees/{id}")
    public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable(value = "id") String employeeId) {

        return employeeRepository.findById(employeeId)
                .flatMap(existingEmployee ->
                	employeeRepository.delete(existingEmployee)
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Employees are Sent to the client as Server Sent Events
    @GetMapping(value = "/stream/employees", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> streamAllEmployees() {
        return employeeRepository.findAll();
    }
}