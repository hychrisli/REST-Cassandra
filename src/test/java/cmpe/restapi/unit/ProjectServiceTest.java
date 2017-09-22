package cmpe.restapi.unit;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.times;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;

import cmpe.restapi.entity.Project;
import cmpe.restapi.error.AppException;
import cmpe.restapi.repository.ProjectRepository;
import cmpe.restapi.service.MapIdService;
import cmpe.restapi.service.ProjectService;
import cmpe.restapi.service.impl.ProjectServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

	@Mock
	private ProjectRepository repo;
	
	@Mock
	private HttpServletRequest req;
	
	@Mock
	private MapIdService mapIdSvc;
	
	@InjectMocks
	private ProjectService projectSvc = new ProjectServiceImpl();
	
	private Project pj1;
	private Project pj2;
	private MapId mId1;
	private MapId mId2;
	private List<Project> pjlst;
	
	@Before
	public void init(){
		pj1 = new Project(1L, "Apache Cassandra", 32300.0f);
		pj2 = new Project(2L, "Apache Spark", 42390.0f);
		pjlst = new ArrayList<Project>();
		pjlst.add(pj1);
		
		mId1 = new BasicMapId();
		mId2 = new BasicMapId();
		mId1.put("id", 1);
		mId2.put("id", 2);
		
		Mockito.when(mapIdSvc.toMapId(1L)).thenReturn(mId1);
		Mockito.when(mapIdSvc.toMapId(2L)).thenReturn(mId2);
		Mockito.when(repo.findOne(mId1)).thenReturn(pj1);
		Mockito.when(repo.findOne(mId2)).thenReturn(null);
		
	}
	
	@Test
	public void testGetAllProjects(){
		Mockito.when(repo.findAll()).thenReturn(pjlst);
		Assert.assertEquals(pjlst, projectSvc.getAllProjects());
	}
	
	@Test
	public void testFindProject(){
		Assert.assertEquals(pj1, projectSvc.findProject(1L));
		Assert.assertEquals(null, projectSvc.findProject(2L));
	}
	
	@Test
	public void testCreateProject(){
		Mockito.when(repo.save(pj2)).thenReturn(pj2);
		
		// Conflict
		Assert.assertEquals(false, projectSvc.createProject(pj1));
		
		// Success
		Assert.assertEquals(true, projectSvc.createProject(pj2));
	}
	
	@Test
	public void testUpdateProject() throws IOException, AppException{
		String jsonPj1 = "{\"name\":\"Apache Kafka\"}";
		BufferedReader br = new BufferedReader(new StringReader(jsonPj1));
		
		HttpServletRequest putReq = new HttpServletRequestWrapper(req);
		
		Project pj1a = new Project(pj1);
		Project pj1b = new Project(pj1.getId(), "Apache Kafka", pj1.getBudget());
		
		Mockito.when(putReq.getReader()).thenReturn(br);
		Mockito.when(repo.findOne(mId1)).thenReturn(pj1a);
		Mockito.when(repo.save(refEq(pj1b))).thenReturn(pj1b);
		
		// Success
		Project pj1c = projectSvc.updateProject(1L, putReq);
		Assert.assertThat(pj1b, new ReflectionEquals(pj1c));
		
		// Not Found
		Assert.assertEquals(null, projectSvc.updateProject(2L, putReq));
	}
	
	@Test
	public void testDeleteProject(){
		
		// Success
		Assert.assertEquals(pj1, projectSvc.deleteProject(1L));
		Mockito.verify(repo, times(1)).delete(mId1);
		
		// Not found
		Assert.assertEquals(null, projectSvc.deleteProject(2L));
		Mockito.verify(repo, times(0)).delete(mId2);
	}
	
}
