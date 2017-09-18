package com.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.restapi.dao.Employee;
import com.restapi.repository.EmployeeRepository;
import com.restapi.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;
	
	@Override
	public List<Employee> getAllEmployees(){
		List<Employee> res = Lists.newArrayList(repo.findAll());
		return res ;
	}

	@Override
	public Boolean insertEmployee(Employee employee) {
		if (findEmployee(employee.getId()) != null)
			return false;
		
		repo.save(employee);
		return true;
	}

	@Override
	public Employee findEmployee(Long id) {
		return repo.findById(id);
	}
}
