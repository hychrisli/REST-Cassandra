package com.restapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.restapi.dao.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	List<Employee> findByLastname(String lastname);
}
