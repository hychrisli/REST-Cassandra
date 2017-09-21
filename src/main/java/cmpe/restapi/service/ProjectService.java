package cmpe.restapi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cmpe.restapi.dao.Project;
import cmpe.restapi.error.AppException;

public interface ProjectService {
	
	public List<Project> getAllProjects();
	
	public Project findProject (Long id);
	
	public Boolean createProject (Project project);
	
	public Project updateProject (Long id, HttpServletRequest req) throws AppException;
	
	public Project deleteProject (Long id);
}
