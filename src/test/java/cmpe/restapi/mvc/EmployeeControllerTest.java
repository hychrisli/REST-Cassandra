package cmpe.restapi.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Matchers.refEq;
import static org.mockito.Matchers.eq;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;
import static cmpe.restapi.config.UrlConstants.EMPLOYEES;
import static cmpe.restapi.config.UrlConstants.EMPLOYEE;
import static cmpe.restapi.config.JsonConstants.KEY_LOCATION;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cmpe.restapi.controller.EmployeeController;
import cmpe.restapi.dao.Employee;
import cmpe.restapi.error.AppException;
import cmpe.restapi.service.EmployeeService;


import org.junit.Before;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Mock
	private EmployeeService employeeSvc;
	
	@Mock
	private HttpServletRequest req;

	@InjectMocks
	private EmployeeController employeeCtrl;

	private Employee emp1;
	private Employee emp2;
	private List<Employee> emplst;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeCtrl).build();

		emp1 = new Employee(1L, "John", "Smith");
		emp2 = new Employee(2L, "Laura", "Johnson");
		emplst = new ArrayList<Employee>();
		emplst.add(emp1);
	}

	@Test
	public void testGetEmployees() throws Exception {
		Mockito.when(employeeSvc.getAllEmployees()).thenReturn(emplst);
		mockMvc.perform(get(EMPLOYEES))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.employees", hasSize(1)))
		.andExpect(jsonPath("$.employees[0].id", equalTo(1)))
		.andExpect(jsonPath("$.employees[0].lastname", equalTo("Smith")));

		Mockito.verify(employeeSvc, times(1)).getAllEmployees();
	}

	@Test
	public void testGetEmployee() throws Exception {
		
		Mockito.when(employeeSvc.findEmployee(1L)).thenReturn(emp1);
		Mockito.when(employeeSvc.findEmployee(2L)).thenReturn(null);
		
		// OK
		mockMvc.perform(get(EMPLOYEE + "/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.employee.lastname",equalTo("Smith")));
		Mockito.verify(employeeSvc, times(1)).findEmployee(1L);
		
		// NOT FOUND
		mockMvc.perform(get(EMPLOYEE + "/2"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testCreateEmployee() throws Exception{
		
		String jsonEmp1 = "{\"id\": 1, \"firstname\":\"John\", \"lastname\":\"Smith\"}";
		String jsonEmp2 = "{\"id\": 2, \"firstname\":\"Laura\", \"lastname\":\"Johnson\"}";
		String jsonEmp3 = "{\"id\" wrong format}";
		
		Mockito.when(employeeSvc.createEmployee(refEq(emp2))).thenReturn(true);
		Mockito.when(employeeSvc.createEmployee(refEq(emp1))).thenReturn(false);
		
		// Test Conflict
		mockMvc.perform(post(EMPLOYEE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonEmp1)).andExpect(status().isConflict());
		
		Mockito.verify(employeeSvc, times(1)).createEmployee(refEq(emp1));
		
		// Test Success
		MvcResult mvcRes = mockMvc.perform(post(EMPLOYEE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonEmp2))
				.andExpect(status().isCreated())
				.andReturn();
		String loc = mvcRes.getResponse().getHeader(KEY_LOCATION);
		Assert.assertTrue(loc.contains(EMPLOYEE + "/2"));
		
		Mockito.verify(employeeSvc, times(1)).createEmployee(refEq(emp2));
		
		// Test Invalid Input
		mockMvc.perform(post(EMPLOYEE)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonEmp3)).andExpect(status().isBadRequest());
		
	}

	@Test
	public void testSuccessfulUpdateEmployee() throws Exception{
		Employee emp1b = new Employee(emp1.getId(), "Kevin", emp1.getLastname());
		
		Mockito.when(employeeSvc.updateEmployee(eq(1L), any())).thenReturn(emp1b);
		mockMvc.perform(put(EMPLOYEE + "/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.employee.firstname", equalTo("Kevin")));
	}
	
	@Test
	public void testUpdateEmployeeNotFound() throws Exception {
		Mockito.when(employeeSvc.updateEmployee(eq(1L), any())).thenReturn(null);
		mockMvc.perform(put(EMPLOYEE + "/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
}
