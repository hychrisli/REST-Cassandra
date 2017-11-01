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

import cmpe.restapi.entity.Employee;
import cmpe.restapi.error.AppException;
import cmpe.restapi.repository.EmployeeRepository;
import cmpe.restapi.service.EmployeeService;
import cmpe.restapi.service.MapIdService;
import cmpe.restapi.service.impl.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository repo;
	
	@Mock
	private HttpServletRequest req;
	
	@Mock
	private MapIdService mapIdSvc;
	
	@InjectMocks
	private EmployeeService employeeSvc = new EmployeeServiceImpl();
	
	private Employee emp1;
	private Employee emp2;
	private MapId mId1;
	private MapId mId2;
	private List<Employee> emplst;
	
	@Before
	public void init(){
		emp1 = new Employee( 1L, "John", "Smith");
		emp2 = new Employee( 2L, "Laura", "Johnson");
		emplst = new ArrayList<Employee>();
		emplst.add(emp1);
		
		mId1 = new BasicMapId();
		mId1.put("id", 1);
		
		mId2 = new BasicMapId();
		mId2.put("id", 2);
		
		Mockito.when(mapIdSvc.toMapId(1L)).thenReturn(mId1);
		Mockito.when(mapIdSvc.toMapId(2L)).thenReturn(mId2);
		Mockito.when(repo.findOne(mId1)).thenReturn(emp1);
		Mockito.when(repo.findOne(mId2)).thenReturn(null);
	}
	
	@Test
	public void testGetAllEmployees(){
		Mockito.when(repo.findAll()).thenReturn(emplst);
		Assert.assertEquals(emplst, employeeSvc.getAllEmployees());
	}
	
	@Test
	public void testFindEmployee(){
		Assert.assertEquals(emp1, employeeSvc.findEmployee(1L));
		Assert.assertEquals(null, employeeSvc.findEmployee(2L));
	}
	
	@Test
	public void testCreateEmployee(){
		Mockito.when(repo.save(emp2)).thenReturn(emp2);
		
		// Conflict
		Assert.assertEquals(false, employeeSvc.createEmployee(emp1));
		
		// Success
		Assert.assertEquals(true, employeeSvc.createEmployee(emp2));
	}
	
	@Test
	public void testUpdateEmployee() throws IOException, AppException{
		
		String jsonEmp1 = "{\"firstname\":\"Kevin\"}";
		BufferedReader br = new BufferedReader(new StringReader(jsonEmp1));
		
		HttpServletRequest putReq = new HttpServletRequestWrapper(req);
		
		Employee emp1a = new Employee(emp1);
		Employee emp1b = new Employee(emp1.getId(), "Kevi", emp1.getLastname());
		
		Mockito.when(putReq.getReader()).thenReturn(br);
		Mockito.when(repo.findOne(mId1)).thenReturn(emp1a);
		Mockito.when(repo.save(refEq(emp1b))).thenReturn(emp1b);
		
		// Success
		Employee emp1c = employeeSvc.updateEmployee(1L,putReq);
		Assert.assertThat(emp1b, new ReflectionEquals(emp1c));
		
		// Not Found
		Assert.assertEquals(null, employeeSvc.updateEmployee(2L, putReq));
	}
	
	@Test
	public void testDeleteEmployee() {
		// delete success
		Assert.assertEquals(emp1, employeeSvc.deleteEmployee(1L));
		Mockito.verify(repo, times(1)).delete(mId1);
		
		// delete not found
		Assert.assertEquals(null, employeeSvc.deleteEmployee(2L));
		Mockito.verify(repo, times(0)).delete(mId2);
	}
}
