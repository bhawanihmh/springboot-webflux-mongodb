
package com.example.webfluxdemo.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author : Bhawani Singh Shekhawat 
 * @date : 06-July-2019
 */
@Document(collection = "employee")
public class Employee {
    @Id
    private String id;

    @NotBlank
    @Size(max = 140)
    private String name;
    
    @NotBlank
    @Size(max = 140)
    private String details;

    @NotNull
    private Date createdAt = new Date();

    public Employee() {

    }
    /**
     * 
     * @param name
     */
    public Employee(String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    };
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
       
}