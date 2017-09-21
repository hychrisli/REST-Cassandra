package cmpe.restapi.service.impl;

import static cmpe.restapi.error.ErrorCode.ERR_INVALID_JSON;
import static cmpe.restapi.error.ErrorCode.ERR_IO_EXCEPTION;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import cmpe.restapi.dao.Employee;
import cmpe.restapi.error.AppException;
import cmpe.restapi.repository.EmployeeRepository;
import cmpe.restapi.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;
	
	/*@Autowired
	private ObjectMapper objectMapper;*/
	
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

	@Override
	public Employee updateEmployee(Long id, HttpServletRequest req) throws AppException {
		Employee employee = findEmployee(id);
		
		if (employee != null){
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				Employee updatedEmployee = objectMapper
						.readerForUpdating(employee)
						.readValue(req.getReader());
				repo.save(updatedEmployee);
				return updatedEmployee;
			} catch (JsonProcessingException e) {
				throw new AppException(ERR_INVALID_JSON, e);
			} catch (IOException e) {
				throw new AppException(ERR_IO_EXCEPTION, e);
			}
		}
		return null;
	}
}
