package cmpe.restapi.service;

import java.util.List;

import cmpe.restapi.dao.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmployees();
	public Employee findEmployee(Long id);
	public Boolean insertEmployee(Employee employee); 
}
