package com.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeSvc;
	
	@GetMapping("/")
	public String greet(){
		return "Spring REST API for CMPE 282";
	}
	
	@GetMapping("/Employees")
	public List<String> getEmployees(){
		return employeeSvc.getRes();
	}
	
}
