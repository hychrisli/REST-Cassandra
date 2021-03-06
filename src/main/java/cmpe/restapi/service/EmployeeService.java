package cmpe.restapi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.cassandra.repository.MapId;

import cmpe.restapi.entity.Employee;
import cmpe.restapi.error.AppException;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	
	public Employee findEmployee(Long id);
	
	public Boolean createEmployee(Employee employee); 
	
	public Employee updateEmployee(Long id, HttpServletRequest req) throws AppException;
	
	public Employee deleteEmployee(Long id);

}
