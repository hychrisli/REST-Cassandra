package cmpe.restapi.controller;

import static cmpe.restapi.config.UrlConstants.PROJECT;
import static cmpe.restapi.config.UrlConstants.PROJECTS;
import static cmpe.restapi.config.UrlConstants.PROJECT_ID;

import static cmpe.restapi.config.JsonConstants.KEY_PROJECT;
import static cmpe.restapi.config.JsonConstants.KEY_PROJECTS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cmpe.restapi.config.JsonResponse;
import cmpe.restapi.dao.Project;
import cmpe.restapi.service.ProjectService;

@RestController
public class ProjectController extends AbstractController {
	
	@Autowired
	ProjectService projectSvc;
	
	@GetMapping(PROJECTS)
	public ResponseEntity<JsonResponse> getProjects(){
		
		List<Project> projects = projectSvc.getAllProjects();
		
		if (projects != null)
			return success(KEY_PROJECTS, projects);
		
		return notFound();
	}
	
	@GetMapping(PROJECT_ID)
	public ResponseEntity<JsonResponse> getProject(@PathVariable Long id){
		
		Project project = projectSvc.findProject(id);
		
		if (project != null)
			return success(KEY_PROJECT, project);
		
		return notFound();
	}	
}
