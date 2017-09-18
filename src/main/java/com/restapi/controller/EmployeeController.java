package com.restapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.mapper.JsonMapper;
import com.restapi.mapper.JsonResponse;
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
	
/*	@GetMapping("/employees")
	public String getEmployees(){
		return jsonMapper.toJson("employees", employeeSvc.getRes());
	}
	
	@PostMapping("/employee")
	public void 
	*/
	
	@GetMapping("/employees")
	public ResponseEntity<JsonResponse> getEmployees(){
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("employees", employeeSvc.getRes()); 
		JsonResponse jr = new JsonResponse(202, "responseData", res);
		return new ResponseEntity<JsonResponse>(jr, HttpStatus.OK);
	}
	
}
