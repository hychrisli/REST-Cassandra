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
	public List<Employee> getRes(){
		List<Employee> res = Lists.newArrayList(repo.findAll());
		return res ;
	}
}
