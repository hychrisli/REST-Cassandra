package cmpe.restapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cmpe.restapi.dao.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	List<Employee> findByLastname(String lastname);
	Employee findById(Long id);
}
