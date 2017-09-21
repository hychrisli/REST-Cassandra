package cmpe.restapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import cmpe.restapi.dao.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	Employee findById(Long id);
	
	@Transactional
	Long deleteById(Long id);
}
