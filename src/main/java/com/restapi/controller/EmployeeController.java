package com.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.mapper.JsonMapper;
import com.restapi.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeSvc;
	
	private JsonMapper jsonMapper = new JsonMapper();
	
	@GetMapping("/")
	public String greet(){
		return "Spring REST API for CMPE 282";
	}
	
	@GetMapping("/Employees")
	public String getEmployees(){
		return jsonMapper.mapObj(employeeSvc.getRes());
	}
	
}
