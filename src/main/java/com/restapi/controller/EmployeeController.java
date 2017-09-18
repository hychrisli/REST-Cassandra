package com.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.dao.Employee;
import com.restapi.mapper.JsonResponse;
import com.restapi.service.EmployeeService;

@RestController
public class EmployeeController extends AbstractController{

	@Autowired
	EmployeeService employeeSvc;
	
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
		
		// Map<String, Object> res = new HashMap<String, Object>();
		// res.put("employees", employeeSvc.getRes()); 
		
		List<Employee> employeeLst = employeeSvc.getAllEmployees();
		
		if (employeeLst != null)
			return success("employees", employeeLst);
		return notFound();
	}
	
	
	 @PostMapping("/employee")
	 public ResponseEntity<JsonResponse> createEmployee (@RequestBody Employee employee){
		 if(employeeSvc.insertEmployee(employee))
		 	return created();
		 return conflict("employee", employee);
	 }
	
}
