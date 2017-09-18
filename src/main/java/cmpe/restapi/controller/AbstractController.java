package cmpe.restapi.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cmpe.restapi.mapper.JsonResponse;

public abstract class AbstractController {

	private final static Logger LOGGER = getLogger(AbstractController.class);

	protected <T> ResponseEntity<JsonResponse> success(String key, T data){
		Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put(key, data);
		JsonResponse jsonResponse = new JsonResponse(HttpStatus.OK.value(), "OK", responseData);
		return genericResponse(jsonResponse, HttpStatus.OK);
	}
	
	protected ResponseEntity<JsonResponse> notFound(){
		JsonResponse jsonResponse = new JsonResponse(HttpStatus.NOT_FOUND.value(), "Not Found", null);
		return genericResponse(jsonResponse, HttpStatus.NOT_FOUND);
	}
	
	protected ResponseEntity<JsonResponse> created(){
		JsonResponse jsonResponse = new JsonResponse(HttpStatus.CREATED.value(), "created", null);
		return genericResponse(jsonResponse, HttpStatus.CREATED);
	}
	
	protected <T> ResponseEntity<JsonResponse> conflict(String key, T data){
		Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put(key, data);
		JsonResponse jsonResponse = new JsonResponse(HttpStatus.CONFLICT.value(), "conflict", responseData);
		return genericResponse(jsonResponse, HttpStatus.CONFLICT);
	}
	
	private ResponseEntity<JsonResponse> genericResponse (JsonResponse jsonResponse, HttpStatus httpStatus){
		return new ResponseEntity<JsonResponse>(jsonResponse, httpStatus);
	}

}
