package cmpe.restapi.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cmpe.restapi.mapper.JsonResponse;

public abstract class AbstractController {

	private final static Logger LOGGER = getLogger(AbstractController.class);

	protected <T> ResponseEntity<JsonResponse> success(String key, T data){
		return genericResponse(new JsonResponse(key, data), HttpStatus.OK);
	}
	
	protected ResponseEntity<JsonResponse> notFound(){
		return genericResponse(new JsonResponse("Error", HttpStatus.NOT_FOUND.name()), HttpStatus.NOT_FOUND);
	}
	
	protected ResponseEntity<JsonResponse> created(String loc){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", loc);
		JsonResponse jsonResponse = new JsonResponse("Msg", "CREATED");
		jsonResponse.addPair("Location", loc);
		return responseWithCustHeaders(jsonResponse, headers, HttpStatus.CREATED);
	}
	
	protected <T> ResponseEntity<JsonResponse> conflict(String key, T data){
		return genericResponse(new JsonResponse(key, data), HttpStatus.CONFLICT);
	}
	
	private ResponseEntity<JsonResponse> genericResponse (JsonResponse jsonResponse, HttpStatus httpStatus){
		return new ResponseEntity<JsonResponse>(jsonResponse, httpStatus);
	}
	
	private ResponseEntity<JsonResponse> responseWithCustHeaders(JsonResponse jsonResponse, HttpHeaders headers, HttpStatus httpStatus){
		return new ResponseEntity<JsonResponse>(jsonResponse, headers, httpStatus);
	}
}
