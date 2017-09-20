package cmpe.restapi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cmpe.restapi.config.AppException;
import cmpe.restapi.dao.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployees();
	
	public Employee findEmployee(Long id);
	
	public Boolean createEmployee(Employee employee); 
	
	public Employee updateEmployee(Long id, HttpServletRequest request) throws AppException;
	
	public Employee deleteEmployee(Long id);
}
