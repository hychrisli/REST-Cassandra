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
import org.springframework.beans.factory.annotation.Autowired;

import cmpe.restapi.dao.Employee;
import cmpe.restapi.repository.EmployeeRepository;
import cmpe.restapi.service.EmployeeService;
import cmpe.restapi.service.impl.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	@Autowired
	private EmployeeRepository repo;

	@InjectMocks
	private EmployeeService employeeSvc = new EmployeeServiceImpl();
	
	private Employee emp1;
	private Employee emp2;
	private List<Employee> emplst;
	
	@Before
	public void init(){
		emp1 = new Employee((long) 1, "John", "Smith");
		emp2 = new Employee((long) 2, "Laura", "Johnson");
		emplst = new ArrayList<Employee>();
		emplst.add(emp1);
	}
	
	@Test
	public void testGetAllEmployees(){
		// System.out.print(emplst.get(0).getFirstname());
		Mockito.when(repo.findAll()).thenReturn(emplst);
		Assert.assertEquals(repo.findAll(), employeeSvc.getAllEmployees());
	}
}
