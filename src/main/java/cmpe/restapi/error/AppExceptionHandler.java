package cmpe.restapi.error;

import static cmpe.restapi.config.JsonConstants.KEY_ERROR;
import static cmpe.restapi.config.JsonConstants.KEY_MESSAGE;
import static cmpe.restapi.error.ErrorCode.ERR_BAD_REQUEST;
import static cmpe.restapi.error.ErrorCode.ERR_INTERNAL_SERVER_ERROR;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cmpe.restapi.config.JsonResponse;
import cmpe.restapi.config.JsonResponseHandler;

@RestControllerAdvice
public class AppExceptionHandler extends JsonResponseHandler{
	
	private final static Logger LOGGER = getLogger(AppExceptionHandler.class);
	
	
	@ExceptionHandler(AppException.class)
	public ResponseEntity<JsonResponse> handleAppException(AppException appException){
		LOGGER.error(appException.getErrorCode().name(), appException);
		return failure(appException.getErrorCode(), appException.getMessage());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<JsonResponse> handleBadRequestException(HttpMessageNotReadableException e) {
		LOGGER.error(ERR_BAD_REQUEST.name(),e);
		return badRequest(e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<JsonResponse> handleException(Exception e){
		LOGGER.error(ERR_INTERNAL_SERVER_ERROR.name(), e);
		return failure(ERR_INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	private ResponseEntity<JsonResponse> failure(ErrorCode errorCode, String msg){
		JsonResponse jsonResponse = 
				new JsonResponse(KEY_ERROR, errorCode.name())
				.addPair(KEY_MESSAGE, msg);
		return genericResponse(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<JsonResponse> badRequest(String msg) {
		JsonResponse jsonResponse = new JsonResponse(KEY_ERROR, ERR_BAD_REQUEST).addPair(KEY_MESSAGE,msg);
		return genericResponse(jsonResponse, HttpStatus.BAD_REQUEST);
	}
	
}
