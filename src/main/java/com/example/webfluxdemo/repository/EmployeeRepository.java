/**
 * 
 */
package com.example.webfluxdemo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.webfluxdemo.model.Employee;

/**
 * @author : Bhawani Singh Shekhawat 
 * @date : 06-July-2019
 */
@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
	public Employee findByName(String name);
    //public List<Employee> findByLastName(String lastName);
}