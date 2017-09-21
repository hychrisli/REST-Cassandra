package cmpe.restapi.controller;

import static cmpe.restapi.config.UrlConstants.EMPLOYEE;
import static cmpe.restapi.config.UrlConstants.EMPLOYEES;
import static cmpe.restapi.config.UrlConstants.EMPLOYEE_ID;

import static cmpe.restapi.config.JsonConstants.KEY_EMPLOYEE;
import static cmpe.restapi.config.JsonConstants.KEY_EMPLOYEES;

import static org.slf4j.LoggerFactory.getLogger;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cmpe.restapi.config.JsonResponse;
import cmpe.restapi.dao.Employee;
import cmpe.restapi.error.AppException;
import cmpe.restapi.service.EmployeeService;


@RestController
public class EmployeeController extends AbstractController {
	
	@Autowired
	EmployeeService employeeSvc;
	
	@GetMapping(EMPLOYEES)
	public ResponseEntity<JsonResponse> getEmployees() {
		List<Employee> employeeLst = employeeSvc.getAllEmployees();

		if (employeeLst != null)
			return success(KEY_EMPLOYEES, employeeLst);
		return notFound();
	}

	@GetMapping(EMPLOYEE_ID)
	public ResponseEntity<JsonResponse> getEmployee(@PathVariable Long id){
		Employee employee = employeeSvc.findEmployee(id);
		if (employee != null)
			return success(KEY_EMPLOYEE, employee);
		return notFound();
	}
	
	@PostMapping(EMPLOYEE)
	public ResponseEntity<JsonResponse> createEmployee(@RequestBody Employee employee) {
		if (employeeSvc.createEmployee(employee))
			return created(EMPLOYEE + "/" + employee.getId());
		return conflict(KEY_EMPLOYEE, employee);
	}

	@PutMapping(EMPLOYEE_ID)
	public ResponseEntity<JsonResponse> updateEmployee(@PathVariable Long id, HttpServletRequest request) throws AppException {
		Employee employee = employeeSvc.updateEmployee(id, request);
		if (employee != null)
			return success(KEY_EMPLOYEE, employee);
		return notFound();
	}
	
	@DeleteMapping(EMPLOYEE_ID)
	public ResponseEntity<JsonResponse> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeSvc.deleteEmployee(id);
		if (employee != null) {
			return success(KEY_EMPLOYEE, employee);
		}
		return notFound();
	}
	
}
