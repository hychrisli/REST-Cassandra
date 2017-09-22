package cmpe.restapi.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Matchers.refEq;
import static org.mockito.Matchers.eq;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;
import static cmpe.restapi.config.UrlConstants.PROJECT;
import static cmpe.restapi.config.JsonConstants.KEY_LOCATION;
import static cmpe.restapi.config.JsonConstants.KEY_PROJECT;
import static cmpe.restapi.config.JsonConstants.KEY_PROJECTS;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cmpe.restapi.controller.ProjectController;
import cmpe.restapi.entity.Employee;
import cmpe.restapi.entity.Project;
import cmpe.restapi.service.ProjectService;


@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private ProjectService projectSvc;
	
	@Mock
	private HttpServletRequest req;
	
	@InjectMocks
	private ProjectController projectCtrl;
	
	private Project pj1;
	private Project pj2;
	private List<Project> pjlst;
	
	@Before
	public void init(){
		mockMvc = MockMvcBuilders.standaloneSetup(projectCtrl).build();
		
		pj1 = new Project(1L, "Apache Cassandra", 32300.0f);
		pj2 = new Project(2L, "Apache Spark", 42390.0f);
		pjlst = new ArrayList<Project>();
		pjlst.add(pj1);
		
	}
	
	@Test
	public void testGetProjectListSucess() throws Exception{
		Mockito.when(projectSvc.getAllProjects()).thenReturn(pjlst);
		mockMvc.perform(get(PROJECT))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$." + KEY_PROJECTS, hasSize(1)))
		.andExpect(jsonPath("$." + KEY_PROJECTS + "[0].id", equalTo(1)))
		.andExpect(jsonPath("$." + KEY_PROJECTS + "[0].name", equalTo("Apache Cassandra")));
		
		Mockito.verify(projectSvc, times(1)).getAllProjects();
	}
	
	@Test
	public void testGetProjectsNotFound() throws Exception{
		Mockito.when(projectSvc.getAllProjects()).thenReturn(new ArrayList<Project>());
		mockMvc.perform(get(PROJECT)).andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetProject() throws Exception {
		Mockito.when(projectSvc.findProject(1L)).thenReturn(pj1);
		Mockito.when(projectSvc.findProject(2L)).thenReturn(null);
		
		// OK
		mockMvc.perform(get(PROJECT + "/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$." + KEY_PROJECT + ".name", equalTo("Apache Cassandra")));
		Mockito.verify(projectSvc, times(1)).findProject(1L);
		
		// NOT FOUND
		mockMvc.perform(get(PROJECT + "/2"))
		.andExpect(status().isNotFound());	
	}
	
	@Test
	public void testCreateProject() throws Exception{
		String jsonPj1 = "{ \"id\" : 1, \"name\" : \"Apache Cassandra\", \"budget\" : 32300.0}";
		String jsonPj2 = "{ \"id\" : 2, \"name\" : \"Apache Spark\", \"budget\" : 42390.0}";
		String jsonPj3 = "{\"id\" wrong format}";
		
		Mockito.when(projectSvc.createProject(refEq(pj1))).thenReturn(false);
		Mockito.when(projectSvc.createProject(refEq(pj2))).thenReturn(true);
		
		// Test Conflict
		mockMvc.perform(post(PROJECT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPj1)).andExpect(status().isConflict());
		
		Mockito.verify(projectSvc, times(1)).createProject(refEq(pj1));
		
		// Test Success
		MvcResult mvcRes = mockMvc.perform(post(PROJECT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPj2))
				.andExpect(status().isCreated())
				.andReturn();
		String loc = mvcRes.getResponse().getHeader(KEY_LOCATION);
		Assert.assertTrue(loc.contains(PROJECT + "/2"));
		
		Mockito.verify(projectSvc, times(1)).createProject(refEq(pj2));
		
		// Test Invalid Input
		mockMvc.perform(post(PROJECT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPj3)).andExpect(status().isBadRequest());	
	}
	
	@Test
	public void testSuccessfulUpdateProject() throws Exception{
		Project pj1b = new Project(pj1.getId(), "Apache Kafka", pj1.getBudget());
		
		Mockito.when(projectSvc.updateProject(eq(1L), any())).thenReturn(pj1b);
		mockMvc.perform(put(PROJECT + "/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$." + KEY_PROJECT + ".name", equalTo("Apache Kafka")));
	}
	
	@Test
	public void testUpdateProjectNotFound() throws Exception {
		Mockito.when(projectSvc.updateProject(eq(2L), any())).thenReturn(null);
		mockMvc.perform(put(PROJECT + "/2").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteProject() throws Exception {
		Mockito.when(projectSvc.deleteProject(1L)).thenReturn(pj1);
		Mockito.when(projectSvc.deleteProject(2L)).thenReturn(null);
		
		// delete success
		mockMvc.perform(delete(PROJECT + "/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$." + KEY_PROJECT + ".id", equalTo(1)))
		.andExpect(jsonPath("$." + KEY_PROJECT + ".name", equalTo("Apache Cassandra")));
		
		// delete not found
		mockMvc.perform(delete(PROJECT + "/2")).andExpect(status().isNotFound());
	}
}
