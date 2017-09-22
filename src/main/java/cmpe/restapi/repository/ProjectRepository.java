package cmpe.restapi.repository;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import cmpe.restapi.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, MapId>{
	
}
