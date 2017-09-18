package cmpe.restapi.unit;

import java.util.List;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import cmpe.restapi.dao.Employee;
import cmpe.restapi.repository.EmployeeRepository;
import cmpe.restapi.service.EmployeeService;
import cmpe.restapi.service.impl.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository repo;

	@InjectMocks
	private EmployeeService employeeSvc = new EmployeeServiceImpl();
	
	private Employee emp1;
	private Employee emp2;
	private List<Employee> emplst;
	
	@Before
	public void init(){
		emp1 = new Employee( 1L, "John", "Smith");
		emp2 = new Employee( 2L, "Laura", "Johnson");
		emplst = new ArrayList<Employee>();
		emplst.add(emp1);
		
		Mockito.when(repo.findById(emp1.getId())).thenReturn(emp1);
		Mockito.when(repo.findById(emp2.getId())).thenReturn(null);
	}
	
	@Test
	public void testGetAllEmployees(){
		Mockito.when(repo.findAll()).thenReturn(emplst);
		Assert.assertEquals(emplst, employeeSvc.getAllEmployees());
	}
	
	public void testFindEmployee(){
		Assert.assertEquals(emp1, employeeSvc.findEmployee(1L));
		Assert.assertEquals(null, employeeSvc.findEmployee(2L));
	}
	
	public void testCreateEmployee(){
		Mockito.when(repo.save(emp2)).thenReturn(emp2);
		
		Assert.assertEquals(false, employeeSvc.createEmployee(emp1));
		Assert.assertEquals(true, employeeSvc.createEmployee(emp2));
	}
	
}
