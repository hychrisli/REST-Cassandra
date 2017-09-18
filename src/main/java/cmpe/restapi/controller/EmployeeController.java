package cmpe.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cmpe.restapi.dao.Employee;
import cmpe.restapi.mapper.JsonResponse;
import cmpe.restapi.service.EmployeeService;

@RestController
public class EmployeeController extends AbstractController {

	@Autowired
	EmployeeService employeeSvc;

	@GetMapping("/")
	public String greet() {
		return "Spring REST API for CMPE 282";
	}

	@GetMapping("/employees")
	public ResponseEntity<JsonResponse> getEmployees() {
		List<Employee> employeeLst = employeeSvc.getAllEmployees();

		if (employeeLst != null)
			return success("employees", employeeLst);
		return notFound();
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<JsonResponse> getEmployee(@PathVariable Long id){
		Employee employee = employeeSvc.findEmployee(id);
		if (employee != null)
			return success("employee", employee);
		return notFound();
	}
	
	@PostMapping("/employee")
	public ResponseEntity<JsonResponse> createEmployee(@RequestBody Employee employee) {
		if (employeeSvc.insertEmployee(employee))
			return created();
		return conflict("employee", employee);
	}

}
