package com.restapi.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
	
	protected ObjectMapper mapper = new ObjectMapper();
	
	public String toJson(String name, Object obj){
		
		String res = "";
		
		try {
			res =  mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res = "{\"" + name + "\":" + res + "}";
		
		return res;
	}
}
