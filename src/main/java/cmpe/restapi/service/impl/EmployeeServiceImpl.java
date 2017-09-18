package cmpe.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cmpe.restapi.dao.Employee;
import cmpe.restapi.repository.EmployeeRepository;
import cmpe.restapi.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;
	
	@Override
	public List<Employee> getAllEmployees(){
		List<Employee> res = Lists.newArrayList(repo.findAll());
		return res ;
	}

	@Override
	public Boolean createEmployee(Employee employee) {
		if (findEmployee(employee.getId()) != null)
			return false;
		repo.save(employee);
		return true;
	}

	@Override
	public Employee findEmployee(Long id) {
		return repo.findById(id);
	}

	@Override
	public Employee deleteEmployee(Long id) {
		Employee employee = findEmployee(id);
		if (employee != null) {
			repo.deleteById(id);
			return employee;
		}
		return null;
	}
}
