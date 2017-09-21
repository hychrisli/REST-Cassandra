package cmpe.restapi.controller;

import static cmpe.restapi.config.JsonConstants.KEY_ERROR;
import static cmpe.restapi.config.JsonConstants.KEY_MESSAGE;
import static cmpe.restapi.config.JsonConstants.KEY_LOCATION;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import cmpe.restapi.config.JsonResponse;
import cmpe.restapi.config.JsonResponseHandler;


public abstract class AbstractController extends JsonResponseHandler {

	protected <T> ResponseEntity<JsonResponse> success(String key, T data){
		return genericResponse(new JsonResponse(key, data), HttpStatus.OK);
	}
	
	protected ResponseEntity<JsonResponse> notFound(){
		return genericResponse(new JsonResponse(KEY_ERROR, HttpStatus.NOT_FOUND.name()), HttpStatus.NOT_FOUND);
	}
	
	protected ResponseEntity<JsonResponse> created(String loc){
		HttpHeaders headers = new HttpHeaders();
		headers.add(KEY_LOCATION, loc);
		JsonResponse jsonResponse = new JsonResponse(KEY_MESSAGE, HttpStatus.CREATED.name());
		jsonResponse.addPair(KEY_LOCATION, loc);
		return responseWithCustHeaders(jsonResponse, headers, HttpStatus.CREATED);
	}
	
	protected <T> ResponseEntity<JsonResponse> conflict(String key, T data){
		return genericResponse(new JsonResponse(key, data), HttpStatus.CONFLICT);
	}

}
