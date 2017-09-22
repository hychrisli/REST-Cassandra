package cmpe.restapi.repository;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.repository.CrudRepository;

import cmpe.restapi.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, MapId> {
}
