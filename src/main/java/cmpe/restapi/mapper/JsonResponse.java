package cmpe.restapi.mapper;

import java.util.Map;

public class JsonResponse {
	
	private int httpStatus;
	private String statMsg;
	private Map<String, Object> responseData;
	
	public JsonResponse(int status, String statMsg, Map<String, Object> responseData){
		this.httpStatus = status;
		this.statMsg = statMsg;
		this.responseData = responseData;
	}
	
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int status) {
		this.httpStatus = status;
	}
	public String getStatMsg() {
		return statMsg;
	}
	public void setStatMsg(String statMsg) {
		this.statMsg = statMsg;
	}
	public Map<String, Object> getResponseData() {
		return responseData;
	}
	public void setResponseData(Map<String, Object> responseData) {
		this.responseData = responseData;
	}
}
