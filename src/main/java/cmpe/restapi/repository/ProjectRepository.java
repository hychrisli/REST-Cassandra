package cmpe.restapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import cmpe.restapi.dao.Project;

public interface ProjectRepository extends CrudRepository<Project, Long>{
	
	Project findById(Long id);
	
	@Transactional
	Long deleteById(Long id); 
}
