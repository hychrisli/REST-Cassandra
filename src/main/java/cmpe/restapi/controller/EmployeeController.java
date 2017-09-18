package cmpe.restapi.controller;

import static cmpe.restapi.config.UrlConstants.EMPLOYEES;
import static cmpe.restapi.config.UrlConstants.EMPLOYEE_ID;
import static cmpe.restapi.config.UrlConstants.EMPLOYEE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cmpe.restapi.dao.Employee;
import cmpe.restapi.mapper.JsonResponse;
import cmpe.restapi.service.EmployeeService;


@RestController
public class EmployeeController extends AbstractController {

	@Autowired
	EmployeeService employeeSvc;

	@GetMapping(EMPLOYEES)
	public ResponseEntity<JsonResponse> getEmployees() {
		List<Employee> employeeLst = employeeSvc.getAllEmployees();

		if (employeeLst != null)
			return success("employees", employeeLst);
		return notFound();
	}

	@GetMapping(EMPLOYEE_ID)
	public ResponseEntity<JsonResponse> getEmployee(@PathVariable Long id){
		Employee employee = employeeSvc.findEmployee(id);
		if (employee != null)
			return success("employee", employee);
		return notFound();
	}
	
	@PostMapping(EMPLOYEE)
	public ResponseEntity<JsonResponse> createEmployee(@RequestBody Employee employee) {
		if (employeeSvc.createEmployee(employee))
			return created(EMPLOYEE + "/" + employee.getId());
		return conflict("employee", employee);
	}

	@PutMapping(EMPLOYEE_ID)
	public ResponseEntity<JsonResponse> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		return null;
	}
	
	@DeleteMapping(EMPLOYEE_ID)
	public ResponseEntity<JsonResponse> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeSvc.deleteEmployee(id);
		if (employee != null) {
			return success("employee", employee);
		}
		return notFound();
	}
	
}
