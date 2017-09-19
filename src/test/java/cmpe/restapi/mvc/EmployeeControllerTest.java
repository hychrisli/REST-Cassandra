package cmpe.restapi.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static cmpe.restapi.config.UrlConstants.EMPLOYEES;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cmpe.restapi.controller.EmployeeController;
import cmpe.restapi.dao.Employee;
import cmpe.restapi.service.EmployeeService;
import cmpe.restapi.service.impl.EmployeeServiceImpl;

import org.junit.Before;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private EmployeeService employeeSvc;
	
	@InjectMocks
	private EmployeeController employeeCtrl;
	
	private Employee emp1;
	private Employee emp2;
	private List<Employee> emplst;
	
	
	@Before
	public void init(){
		mockMvc = MockMvcBuilders.standaloneSetup(employeeCtrl).build();
		
		emp1 = new Employee( 1L, "John", "Smith");
		emp2 = new Employee( 2L, "Laura", "Johnson");
		emplst = new ArrayList<Employee>();
		emplst.add(emp1);
	}
	
	@Test
	public void testGetEmployees() throws Exception{
		Mockito.when(employeeSvc.getAllEmployees()).thenReturn(emplst);
		mockMvc.perform(get(EMPLOYEES)).andExpect(status().isOk());
	}
	
}

