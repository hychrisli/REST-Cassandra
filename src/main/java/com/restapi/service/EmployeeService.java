package com.restapi.service;

import java.util.List;

import com.restapi.dao.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployees();
	public Employee findEmployee(Long id);
	public Boolean insertEmployee(Employee employee); 
}
