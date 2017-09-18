package com.restapi.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.restapi.dao.Employee;

public class EmployeeMapper extends JsonMapper {
	
	public Employee toPojo(String jsonStr){
		
		Employee employee = null;
		
		try {
			employee = mapper.readValue(jsonStr, Employee.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employee;
	}
}
