package cmpe.restapi.controller;

import static org.slf4j.LoggerFactory.getLogger;
import static cmpe.restapi.config.JsonConstants.KEY_ERROR;
import static cmpe.restapi.config.JsonConstants.KEY_MESSAGE;
import static cmpe.restapi.error.ErrorCode.ERR_BAD_REQUEST;
import static cmpe.restapi.error.ErrorCode.ERR_INTERNAL_SERVER_ERROR;
import static cmpe.restapi.config.JsonConstants.KEY_LOCATION;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cmpe.restapi.config.JsonResponse;
import cmpe.restapi.config.JsonResponseHandler;
import cmpe.restapi.error.AppException;
import cmpe.restapi.error.ErrorCode;

public abstract class AbstractController extends JsonResponseHandler {

	private final static Logger LOGGER = getLogger(AbstractController.class);

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
