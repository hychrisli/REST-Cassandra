package com.restapi.mapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
	ObjectMapper mapper = new ObjectMapper();
	
	public String mapObj(Object lst){
		
		String res = "";
		
		try {
			res =  mapper.writeValueAsString(lst);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
}
